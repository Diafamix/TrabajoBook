package com.optimissa.BookShelfApi.repositories;

import com.optimissa.BookShelfApi.model.RentBook;
import org.springframework.data.repository.CrudRepository;

public interface RentedRepo extends CrudRepository<RentBook, Long> {

    Iterable<RentBook> findAllByUserUsername(String username);

}
