package com.optimissa.BookShelfApi.Controller;

import com.optimissa.BookShelfApi.model.Book;
import com.optimissa.BookShelfApi.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
 *
 *  @author carlos.cueva
 * @version 12/07/22
 *
 * */

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/all")
    public Iterable<Book> showBooks() {
        return bookRepo.findAll();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> showById(@PathVariable String id) {

        Book book = null;
        Map<String, Object> response = new HashMap<>();

        try {
            book = bookRepo.findBookById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        if (book == null) {
            response.put("Mensaje", "Book_Id ".concat(id.concat(" no existe en la base de datos. ")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> showByName(@PathVariable String name) {

        Book book = null;
        Map<String, Object> response = new HashMap<>();

        try {
            book = bookRepo.findBookByTitle(name);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        if (book == null) {
            response.put("Mensaje", "Book_Title ".concat(name.concat(" no existe en la base de datos. ")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBook(@RequestBody Book book) {

        Book BookNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            BookNew = bookRepo.save(book);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        response.put("Mensaje", "El Book ha sido creado con exito. ");
        response.put("Book", book);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable String id) {

        Book bookAuctual = bookRepo.findBookById(id);
        Book bookUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if (bookAuctual == null) {
            response.put("Mensaje", "No se pudo editar el Book con id: ".concat(id.concat(" No existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

            bookAuctual.setBookDetails(book.getBookDetails());
            bookAuctual.setTitle(book.getTitle());
            bookAuctual.setAuthor(book.getAuthor());
            bookAuctual.setSamples(book.getSamples());

            bookUpdate = bookRepo.save(bookAuctual);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El Book ha sido actualizado con exito. ");
        response.put("Book", bookUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {

        Map<String, Object> response = new HashMap<>();

        try {
            bookRepo.deleteById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al actualizar el Book en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El Book ha sido eliminado con exito. ");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}