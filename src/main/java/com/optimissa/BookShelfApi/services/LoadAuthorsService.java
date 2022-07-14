package com.optimissa.BookShelfApi.services;

import com.optimissa.BookShelfApi.model.Author;
import com.optimissa.BookShelfApi.repositories.AuthorRepo;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Builder
public class LoadAuthorsService {

    @Autowired
    private AuthorRepo authorRepo;

    public void load() {
        String[] names = {"J. K. Rowling", "Federico García Lorca", "Pedro Calderon de la Barca", "J. R. R. Tolkien",
                "Elisabet Benavent", "Pedro Salinas", "Luis Cernuda", "Rafael Alberti"};

        for (String name : names) {
            authorRepo.save(Author.builder()
                    .name(name)
                    .build());
        }

    }

}
