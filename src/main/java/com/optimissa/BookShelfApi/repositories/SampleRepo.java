package com.optimissa.BookShelfApi.repositories;


import com.optimissa.BookShelfApi.model.Sample;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SampleRepo extends CrudRepository<Sample, Long> {

    @Query(value = "SELECT h FROM Sample h WHERE h.book.id = ?1")
    Iterable<Sample> findAllByBook(long bookId);

}
