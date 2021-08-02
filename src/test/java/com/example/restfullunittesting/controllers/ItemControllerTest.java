package com.example.restfullunittesting.controllers;

import com.example.restfullunittesting.businnes.ItemBusinessService;
import com.example.restfullunittesting.model.Item;
import com.example.restfullunittesting.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemBusinessService businessService;


    @Test
    void dummyItem() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/items/dummy-item")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Ball\",\"price\":10,\"quantity\":100}"))
                .andReturn();
    }

    @Test
    void itemFromBusinessService() throws Exception {
        when(businessService.findById(1L)).thenReturn(new Item(1L,"Item2", 10, 10));
        mockMvc.perform(
                get("/api/items/item-from-business-service/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"id\":1,\"name\":\"Item2\",\"price\":10,\"quantity\":10}"));
    }
    @Test
    void findAllItems() throws Exception {
        List<Item> itemList =Arrays.asList(
                new Item(1L,"Item1",10,10),
                new Item(2L,"Item2",20,10));
        when(businessService.findAllItems()).thenReturn(itemList);
        mockMvc.perform(get("/api/items/findAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemList)));
    }

    @Test
    void save() throws Exception {
        Item item = new Item(1L, "newItem", 10, 10);
        when(businessService.save(any())).thenReturn(item);

        mockMvc.perform(
                post("/api/items/save")
                        .content(objectMapper.writeValueAsString(item))
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(item)));
    }
}