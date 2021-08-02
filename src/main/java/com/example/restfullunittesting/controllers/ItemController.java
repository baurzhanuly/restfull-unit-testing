package com.example.restfullunittesting.controllers;

import com.example.restfullunittesting.businnes.ItemBusinessService;
import com.example.restfullunittesting.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/items")
public class ItemController {


    private ItemBusinessService businessService;

    @Autowired
    public ItemController(ItemBusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/dummy-item")
    public Item findOne(){
        return new Item(1L, "Ball", 10, 100);
    }
    @GetMapping("/item-from-business-service/{id}")
    public Item itemFromBusinessService(@PathVariable Long id){
        return businessService.findById(id);
    }

    @GetMapping("/findAll")
    public List<Item> findAllItems(){
        return businessService.findAllItems();
    }

    @PostMapping("/save")
    public Item save(@RequestBody Item item){
        return businessService.save(item);
    }
}
