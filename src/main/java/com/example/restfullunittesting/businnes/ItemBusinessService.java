package com.example.restfullunittesting.businnes;

import com.example.restfullunittesting.model.Item;
import com.example.restfullunittesting.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemBusinessService {


    private ItemRepository itemRepository;

    @Autowired
    public ItemBusinessService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.orElseThrow(() -> new IllegalStateException("item not found"));
    }

    public List<Item> findAllItems(){
        List<Item> items = itemRepository.findAll();
        for (Item item: items) {
            item.setValue(item.getPrice() * item.getQuantity());
        }
        return items;
    }

    public Item save(Item item){
        if (item.getId() != null) {
            throw new IllegalStateException("item exists");
        }
        return itemRepository.save(item);
    }
}
