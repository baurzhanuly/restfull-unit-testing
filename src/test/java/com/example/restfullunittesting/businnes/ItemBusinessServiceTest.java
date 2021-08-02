package com.example.restfullunittesting.businnes;

import com.example.restfullunittesting.model.Item;
import com.example.restfullunittesting.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemBusinessServiceTest {

    @InjectMocks
    private ItemBusinessService businessService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() {
        Item item = new Item(1L, "Item1", 10,10);
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = businessService.findById(1L);

        assertThat(result.getPrice()).isEqualTo(10);
        assertThat(result.getQuantity()).isEqualTo(10);
    }

    @Test
    void findById_ItemNotFound() {
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class ,() -> {
            businessService.findById(1L);
        }, "item not found");
    }

    @Test
    void findAll() {
        Mockito.when(itemRepository.findAll()).thenReturn(
                Arrays.asList(new Item(1L,"Item1",10,10), new Item(2L,"Item2",20,10)));

        List<Item> itemList = businessService.findAllItems();

        Assertions.assertEquals(itemList.get(0).getValue(),100);
        Assertions.assertEquals(itemList.get(1).getValue(),200);
        Assertions.assertEquals(itemList.size(),2);
    }
}