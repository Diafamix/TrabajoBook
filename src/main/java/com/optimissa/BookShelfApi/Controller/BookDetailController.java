package com.optimissa.BookShelfApi.Controller;

import com.optimissa.BookShelfApi.model.BookDetails;
import com.optimissa.BookShelfApi.repositories.BookDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
 * @author carlos.cueva
 * @version 13/07/22
 *
 * */
@RestController
@RequestMapping("/bookDetail")
public class BookDetailController {

    @Autowired
    private BookDetailsRepo bookDetailsRepo;

    @GetMapping("/all")
    public Iterable<BookDetails> showBookDetails() {
        return bookDetailsRepo.findAll();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> showBooKDetailsById(@PathVariable Long id) {

        BookDetails bookDetails = null;
        Map<String, Object> response = new HashMap<>();

        try {
            bookDetails = bookDetailsRepo.findBookDetailsById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        if (bookDetails == null) {
            response.put("Mensaje", "BookDetails_Id ".concat(id.toString().concat(" no existe en la base de datos. ")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<BookDetails>(bookDetails, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBookDetails(BookDetails bookDetails) {

        BookDetails bookDetailsNew = null;

        Map<String, Object> response = new HashMap<>();

        try {
            bookDetailsNew = bookDetailsRepo.save(bookDetails);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        response.put("Mensaje", "El Author ha sido creado con exito. ");
        response.put("Author", bookDetails);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBookDetails(@RequestBody BookDetails bookDetails, @PathVariable Long id) {

        BookDetails bookDetailsAuctual = bookDetailsRepo.findBookDetailsById(id);
        BookDetails bookDeletailsUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if (bookDetailsAuctual == null) {
            response.put("Mensaje", "No se pudo editar el BookDetails con id: ".concat(id.toString().concat(" No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            bookDetailsAuctual.setLanguage(bookDetails.getLanguage());
            bookDetailsAuctual.setPrice(bookDetails.getPrice());
            bookDetailsAuctual.setFamily(bookDetails.getFamily());
            bookDetailsAuctual.setDescription(bookDetails.getDescription());

            bookDeletailsUpdate = bookDetailsRepo.save(bookDetailsAuctual);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El BookDetails ha sido actualizado con exito. ");
        response.put("BookDetails", bookDeletailsUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookDetails(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            bookDetailsRepo.deleteById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al actualizar el BookDetails en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El BookDetails ha sido eliminado con exito. ");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
