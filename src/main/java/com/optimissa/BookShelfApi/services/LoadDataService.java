package com.optimissa.BookShelfApi.services;

import com.optimissa.BookShelfApi.model.*;
import com.optimissa.BookShelfApi.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Slf4j
@Service
public class LoadDataService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SampleRepo sampleRepo;

    @Autowired
    private BookDetailsRepo bookDetailsRepo;

    @Autowired
    private BookHistoryRepo bookHistoryRepo;

    public void load() {
        //loadShops();
        //loadAuthors();
        loadBooks();
        loadSamples();
        loadUsers();
        loadLogs();

        /*shopRepo.findById(1L).ifPresent(shop -> log.info(shop.toString()));
        bookRepo.findById("000001S").ifPresent(book -> log.info(book.toString()));
        sampleRepo.findById(1L).ifPresent(sample -> log.info(sample.toString())); */
    }

    private void loadShops() {
        Shop shop = Shop.builder()
                .name("Tiena Antonio and Company Inc")
                .direction("Barbate")
                .city("Cadiz")
                .build();

        shopRepo.save(shop);
    }

    private void loadAuthors() {
        Author author = Author.builder()
                .name("Antonio Calderon")
                .build();

        authorRepo.save(author);
    }

    private void loadBooks() {

        Book book = Book.builder()
                .id("000001S")
                .title("Las Aventuras de Antonio y Compañia")
                .author(authorRepo.findById(1L).get())
                .bookDetails(loadBookDetails())
                .build();

        bookRepo.save(book);
    }

    private void loadSamples() {
        Sample sample = Sample.builder()
                .shop(shopRepo.findById(1L).get())
                .book(bookRepo.findById("000001S").get())
                .build();

        sampleRepo.save(sample);
    }

    private BookDetails loadBookDetails() {
        BookDetails details = BookDetails.builder()
                .family("Terror")
                .language("ES")
                .description("Las historias de antonio")
                .publication(Calendar.getInstance().getTime())
                .price(500000.5)
                .build();

        bookDetailsRepo.save(details);

        return details;
    }

    private void loadUsers() {
        User user = User.builder()
                .mail("sergio.bernal@optimissa.com")
                .username("sergio bernal")
                .password("1234")
                .build();

        userRepo.save(user);
    }

    private void loadLogs() {
        BookHistory bookHistory = BookHistory.builder()
                .user(userRepo.findById("sergio.bernal@optimissa.com").get())
                .book(bookRepo.findById("000001S").get())
                .date(Calendar.getInstance().getTime())
                .type(BookHistory.Type.BUY)
                .quantity(2)
                .build();

        log.info(bookHistory.toString());

        bookHistoryRepo.save(bookHistory);
    }

}
