package com.optimissa.BookShelfApi.services;

import com.optimissa.BookShelfApi.model.Shop;
import com.optimissa.BookShelfApi.repositories.ShopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadShopService {

    @Autowired
    private ShopRepo repo;

    public void LoadShop() {
        Shop shop = Shop.builder()
                .name("Libreria Manolo")
                .city("Madrid")
                .direction("Calle Juan Bravo, 11")
                .build();

        repo.save(shop);

        shop = Shop.builder()
                .name("Libreria Alcala")
                .city("Alcala de Henares")
                .direction("Calle de las Vaqueras")
                .build();

        repo.save(shop);

        shop = Shop.builder()
                .name("La Casa del Libro")
                .city("Madrid")
                .direction("Gran Via,30")
                .build();

        repo.save(shop);
    }

}
