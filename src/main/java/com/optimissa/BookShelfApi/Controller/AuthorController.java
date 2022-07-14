package com.optimissa.BookShelfApi.Controller;

import com.optimissa.BookShelfApi.model.Author;
import com.optimissa.BookShelfApi.repositories.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*

 * @author carlos.cueva
 * @version 12/07/22
 *
 * */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorRepo authorRepo;

    @GetMapping("/all")
    public Iterable<Author> showAuthors() {
        return authorRepo.findAll();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        Author author = null;
        Map<String, Object> response = new HashMap<>();

        try {
            author = authorRepo.findAuthorById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        if (author == null) {
            response.put("Mensaje", "Author_Id ".concat(id.toString().concat(" no existe en la base de datos. ")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);

    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<?> showByName(@PathVariable String name) {
        Author author = null;
        Map<String, Object> response = new HashMap<>();

        try {
            author = authorRepo.findAuthorByName(name);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        if (author == null) {
            response.put("Mensaje", "Author_Name ".concat(name.concat(" no existe en la base de datos. ")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {

        Author authorNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            authorNew = authorRepo.save(author);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }

        response.put("Mensaje", "El Author ha sido creado con exito. ");
        response.put("Author", author);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author, @PathVariable Long id) {

        Author authorAuctual = authorRepo.findById(id).orElse(null);
        Author authorUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if (authorAuctual == null) {
            response.put("Mensaje", "No se pudo editar el cliente con id: ".concat(id.toString().concat(" No existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

            authorAuctual.setBooks(author.getBooks());
            authorAuctual.setName(author.getName());

            authorUpdate = authorRepo.save(authorAuctual);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al realizar la consulta en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El cliente ha sido actualizado con exito. ");
        response.put("Author", authorUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            authorRepo.deleteAuthorById(id);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al actualizar el Author en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El Author ha sido eliminado con exito. ");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<?> deleteAuthor(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();

        try {
            authorRepo.deleteAuthorByName(name);
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error al actualizar el Author en la base de datos. ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("Mensaje", "El Author ha sido eliminado con exito. ");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
