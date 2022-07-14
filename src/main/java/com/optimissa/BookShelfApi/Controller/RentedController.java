package com.optimissa.BookShelfApi.Controller;

import com.optimissa.BookShelfApi.model.RentBook;
import com.optimissa.BookShelfApi.repositories.RentedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rented")
public class RentedController {

    @Autowired
    private RentedRepo rentedRepo;

    @GetMapping("/all")
    public Iterable<RentBook> showRentBooks() {
        return rentedRepo.findAll();
    }

    @GetMapping("/byId/{id}")
    public RentBook showRentedBySampleId(@PathVariable long id) {
        return rentedRepo.findById(id).orElse(null);
    }

    @GetMapping("/byUsername/{username}")
    public Iterable<RentBook> showRentedByUserId(@PathVariable String username) {
        return rentedRepo.findAllByUserUsername(username);
    }

    @PostMapping("/create")
    public RentBook createRented(@RequestBody RentBook rentBook) {
        return rentedRepo.save(rentBook);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRented(@PathVariable long id) {
        rentedRepo.deleteById(id);
    }
}
