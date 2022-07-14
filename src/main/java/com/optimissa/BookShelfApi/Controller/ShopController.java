package com.optimissa.BookShelfApi.Controller;

import com.optimissa.BookShelfApi.model.Shop;
import com.optimissa.BookShelfApi.repositories.ShopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopRepo shoprepo;


    @GetMapping("/all")
    public Iterable<Shop> getAllShops() {

        return shoprepo.findAll();
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<Shop> byId(long id) {
        return shoprepo.findById(id)
                .map(shop -> new ResponseEntity<>(shop, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public void addShop(@RequestBody Shop shop) {

        shoprepo.save(shop);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteShopById(@RequestBody long id) {


        return shoprepo.existsById(id)
                ? new ResponseEntity<>(shoprepo.deleteById(id), HttpStatus.FOUND)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
