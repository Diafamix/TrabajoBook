package com.optimissa.BookShelfApi.repositories;

import com.optimissa.BookShelfApi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, String> {

    User findUserByUsername(String username);

    User findUserByMail(String mail);

}
