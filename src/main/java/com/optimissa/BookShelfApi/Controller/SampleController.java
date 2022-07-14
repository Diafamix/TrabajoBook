package com.optimissa.BookShelfApi.Controller;


import com.optimissa.BookShelfApi.model.Sample;
import com.optimissa.BookShelfApi.repositories.SampleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sample")
public class SampleController {

    @Autowired
    private SampleRepo sampleRepo;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Sample>> all() {
        return new ResponseEntity<>(sampleRepo.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("byId{id}")
    public ResponseEntity<Sample> byId(@PathVariable("id") long id) {
        return sampleRepo.findById(id)
                .map(sample -> new ResponseEntity<>(sample, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/byBook/{bookId}")
    public ResponseEntity<Iterable<Sample>> byBook(@PathVariable("bookId") long bookId) {
        return new ResponseEntity<>(sampleRepo.findAllByBook(bookId), HttpStatus.FOUND);
    }

    @DeleteMapping("/byBook/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        if (sampleRepo.existsById(id)) {
            sampleRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
