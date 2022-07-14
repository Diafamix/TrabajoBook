package com.optimissa.BookShelfApi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimissa.BookShelfApi.model.Author;
import com.optimissa.BookShelfApi.model.Book;
import com.optimissa.BookShelfApi.repositories.AuthorRepo;
import com.optimissa.BookShelfApi.repositories.BookDetailsRepo;
import com.optimissa.BookShelfApi.repositories.BookRepo;
import com.optimissa.BookShelfApi.services.parsers.GoogleJsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class LoadBooksService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate rest;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BookDetailsRepo bookDetailsRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private GoogleJsonParser jsonParser;

    /**
     * Retrieves books by current authors on database and loads them on database
     */
    public void load() throws JsonProcessingException {
        log.info("Loading books....");
        for (Author author : authorRepo.findAll()) {
            for (Book book : getBooksFromAuthor(author)) {
                bookDetailsRepo.save(book.getBookDetails());
                bookRepo.save(book);
            }
        }
        log.info("Books loaded successfully");
    }

    private List<Book> getBooksFromAuthor(Author author) throws JsonProcessingException {
        String url = "https://www.googleapis.com/books/v1/volumes?q=inauthor:" + author.getName();
        String str = rest.getForObject(url, String.class);

        return jsonParser.parseJsonAsBooks(mapper.readTree(str));
    }

}
