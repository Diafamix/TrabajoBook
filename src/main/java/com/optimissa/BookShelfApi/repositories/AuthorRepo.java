package com.optimissa.BookShelfApi.repositories;


import com.optimissa.BookShelfApi.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepo extends CrudRepository<Author, Long> {

    @Transactional
    Author findAuthorById(Long id);

    Author findAuthorByName(String name);

    @Transactional
    void deleteAuthorById(Long id);

    @Transactional
    void deleteAuthorByName(String name);
}
