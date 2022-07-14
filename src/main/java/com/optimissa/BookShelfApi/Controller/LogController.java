package com.optimissa.BookShelfApi.Controller;


import com.optimissa.BookShelfApi.model.BookHistory;
import com.optimissa.BookShelfApi.model.BookHistoryPk;
import com.optimissa.BookShelfApi.repositories.BookHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class
LogController {

    @Autowired
    private BookHistoryRepo bookHistoryRepo;

    @GetMapping("/all")
    public Iterable<BookHistory> showAll() {
        return bookHistoryRepo.findAll();
    }

    @GetMapping("/byId/{id}")
    public BookHistory showById(@RequestBody BookHistoryPk id) {
        return bookHistoryRepo.findById(id).orElse(null);
    }

    @GetMapping("/allByUsername/{username}")
    public Iterable<BookHistory> showByUsername(@PathVariable("username") String username) {
        return bookHistoryRepo.findAllByUserUsername(username);
    }

    @GetMapping("allByBookIsbn/{isbn}")
    public Iterable<BookHistory> showByIsbn(@PathVariable("isbn") String isbn) {
        return bookHistoryRepo.findAllByBookId(isbn);
    }

}


