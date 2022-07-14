package com.optimissa.BookShelfApi.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimissa.BookShelfApi.model.Book;
import com.optimissa.BookShelfApi.repositories.BookDetailsRepo;
import com.optimissa.BookShelfApi.repositories.BookRepo;
import com.optimissa.BookShelfApi.services.parsers.GoogleJsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class MockFileParser {

    @Autowired
    private ObjectMapper parser;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BookDetailsRepo bookDetailsRepo;

    @Autowired
    private GoogleJsonParser googleJsonParser;

    public void parseFiles() {
        Stream.of("elisabeth", "federico_garcia_lorca", "JK_ROWNLING", "luis_cernuda",
                        "pedro_calderon_barca", "pedro_salinas", "rafael_alberti", "tolkien")
                .forEach(s -> {
                    String path = String.format("files/jsons/%s.json", s);
                    try {
                        parseFile(new File(path));
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                });
    }

    private void parseFile(File file) throws IOException {
        JsonNode json = parser.readTree(file);
        log.info(json.toString());

        List<Book> books = googleJsonParser.parseJsonAsBooks(json);
        log.info(books.toString());

        books.forEach(book -> bookDetailsRepo.save(book.getBookDetails()));
        bookRepo.saveAll(books);
    }

}
