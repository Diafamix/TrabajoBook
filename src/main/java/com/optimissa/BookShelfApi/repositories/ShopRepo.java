package com.optimissa.BookShelfApi.repositories;


import com.optimissa.BookShelfApi.model.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepo extends CrudRepository<Shop, Long> {

    Long deleteById(long id);
}
