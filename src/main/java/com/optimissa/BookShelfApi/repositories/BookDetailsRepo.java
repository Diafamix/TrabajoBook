package com.optimissa.BookShelfApi.repositories;


import com.optimissa.BookShelfApi.model.BookDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookDetailsRepo extends CrudRepository<BookDetails, Long> {

    @Transactional
    BookDetails findBookDetailsById(Long id);

    @Transactional
    void deleteById(Long id);
}
