package com.optimissa.BookShelfApi.services;

import com.optimissa.BookShelfApi.model.Book;
import com.optimissa.BookShelfApi.model.Sample;
import com.optimissa.BookShelfApi.model.Shop;
import com.optimissa.BookShelfApi.repositories.BookRepo;
import com.optimissa.BookShelfApi.repositories.SampleRepo;
import com.optimissa.BookShelfApi.repositories.ShopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LoadSamplesService {

    @Autowired
    private SampleRepo sampleRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private Random random;

    /**
     * Creates new samples randomly and saves them on the database.
     */
    public void load() {
        List<Shop> shops = getShops();

        for (Book book : bookRepo.findAll()) {
            for (int i = 0; i < randomNumberBetween(1, 30); i++) {
                Sample sample = Sample.builder()
                        .book(book)
                        .shop(getRandomShop(shops))
                        .build();

                sampleRepo.save(sample);
            }
        }
    }

    private List<Shop> getShops() {
        List<Shop> shops = new ArrayList<>();
        shopRepo.findAll().forEach(shops::add);

        return shops;
    }

    private int randomNumberBetween(int start, int end) {
        return (int) (start + (random.nextDouble() * (end - start)));
    }

    private Shop getRandomShop(List<Shop> shops) {
        int index = randomNumberBetween(0, shops.size());

        return shops.get(index);
    }

}
