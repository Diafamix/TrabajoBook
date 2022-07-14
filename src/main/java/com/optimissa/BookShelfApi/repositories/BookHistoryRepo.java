package com.optimissa.BookShelfApi.repositories;

import com.optimissa.BookShelfApi.model.BookHistory;
import com.optimissa.BookShelfApi.model.BookHistoryPk;
import org.springframework.data.repository.CrudRepository;

public interface BookHistoryRepo extends CrudRepository<BookHistory, BookHistoryPk> {

    Iterable<BookHistory> findAllByUserUsername(String username);

    Iterable<BookHistory> findAllByBookId(String isbn);

    BookHistory findBookHistoryById(BookHistoryPk bookHistoryPk);
}
