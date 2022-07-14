package com.optimissa.BookShelfApi;

import com.optimissa.BookShelfApi.repositories.AuthorRepo;
import com.optimissa.BookShelfApi.repositories.BookDetailsRepo;
import com.optimissa.BookShelfApi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShelfApplication.class, args);
    }

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookDetailsRepo bookDetailsRepo;

    @Bean
    public CommandLineRunner runner(LoadShopService shopsService,
                                    LoadAuthorsService authorsService,
                                    LoadBooksService booksService,
                                    LoadSamplesService samplesService,
                                    LoadDataService dataService,
                                    MockFileParser mockFileParser
    ) {
        return args -> {
            shopsService.LoadShop();
            authorsService.load();
            dataService.load();


            //booksService.load();
            mockFileParser.parseFiles();
            samplesService.load();
        };

    }


}
