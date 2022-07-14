package com.optimissa.BookShelfApi.Controller;

import com.optimissa.BookShelfApi.model.User;
import com.optimissa.BookShelfApi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    //Registro usuarios
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(String mail, String userName, String password) {
        User user = new User(mail, userName, password);

        User userNameTest = userRepo.findUserByUsername(userName);
        User userMailTest = userRepo.findUserByMail(mail);

        if (userNameTest != null || userMailTest != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
