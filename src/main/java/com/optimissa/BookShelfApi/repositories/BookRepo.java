package com.optimissa.BookShelfApi.repositories;


import com.optimissa.BookShelfApi.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepo extends CrudRepository<Book, String> {

    Book findBookByTitle(String title);

    Book findBookById(String id);

}
