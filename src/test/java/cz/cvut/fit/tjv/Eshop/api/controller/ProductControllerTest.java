package cz.cvut.fit.tjv.Eshop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.tjv.Eshop.business.ProductService;
import cz.cvut.fit.tjv.Eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    Product product1 = new Product("product1", 145);
    Product product2 = new Product("product2", 140);
    Product product3 = new Product("product3", 135);
    Product product4 = new Product("product4", 111);
    List<Product> products = List.of(product1, product2, product3, product4);
    ProductDTO productDTO = new ProductDTO("product1", 145, 1L);

    @BeforeEach
    void setup(){

        Mockito.when(productService.getProducts()).thenReturn(products);
        Mockito.when(productService.getProductsWithPriceLowerThan(140)).thenReturn(products);
        Mockito.when(productService.getById(1L)).thenReturn(product1);
        Mockito.when(productService.exists(1L)).thenReturn(true);

    }


    @Test
    void getProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("product1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("product2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("product3")));
    }

    @Test
    void getProductsWithPriceLowerThan() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product?highest_price=140").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("product1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("product2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("product3")));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("product1")));
    }

    @Test
    void deleteByIdFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDTO);

        Mockito.when(productService.updateById(1L, productDTO )).thenReturn(product1);
        Mockito.when(productService.exists(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/product/1").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("product1")));
    }

    @Test
    void registerNewProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDTO);

        Mockito.when(productService.addNewProduct( product1 )).thenReturn(product1);

        mockMvc.perform(MockMvcRequestBuilders.post("/product").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("product1")));
    }

}