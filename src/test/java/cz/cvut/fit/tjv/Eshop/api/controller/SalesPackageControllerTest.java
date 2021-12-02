package cz.cvut.fit.tjv.Eshop.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.tjv.Eshop.business.ProductService;
import cz.cvut.fit.tjv.Eshop.business.SalesPackageService;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SalesPackageController.class)
class SalesPackageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesPackageService salesPackageService;

    @MockBean
    private ProductService productService;

    Product product1 = new Product("product1", 145);
    Product product2 = new Product("product2", 145);
    Product product3 = new Product("product3", 145);

    Set<Product> products = new HashSet<Product>(Arrays.asList(product1, product2));

    Set<Product> products2 = new HashSet<Product>(Arrays.asList(product1, product2, product3));
    SalesPackage salesPackage = new SalesPackage(products, 19);
    SalesPackage salesPackage2 = new SalesPackage(products2, 15);
    SalesPackageDTO salesPackageDTO = new SalesPackageDTO(products2, 15, 1L);
    Collection<SalesPackage> salesPackages = List.of(salesPackage, salesPackage2);

    @BeforeEach
    void setup(){
        Mockito.when(salesPackageService.getSalesPackages()).thenReturn(salesPackages);
        Mockito.when(salesPackageService.getSalesPackagesContainingProduct(1L)).thenReturn(salesPackages);
        Mockito.when(salesPackageService.getById(1L)).thenReturn(salesPackage);
        Mockito.when(salesPackageService.updateById(1L, salesPackageDTO, false )).thenReturn(salesPackage);
        Mockito.when(salesPackageService.updateById(1L, salesPackageDTO, true )).thenReturn(salesPackage);
        Mockito.when(salesPackageService.exists(1L)).thenReturn(true);
        Mockito.when(productService.checkProductsExists(salesPackageDTO.getProducts())).thenReturn(true);

    }

    @Test
    void getSalesPackages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/package").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sale", Matchers.is(19)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sale", Matchers.is(15)));
    }

    @Test
    void getSalesPackagesContainingProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/package?product_id=1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sale", Matchers.is(19)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sale", Matchers.is(15)));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/package/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sale", Matchers.is(19)));
    }

    @Test
    void updateByIdNoMerge() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(salesPackageDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/package/1").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sale", Matchers.is(19)));
    }
    @Test
    void updateByIdMerge() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(salesPackageDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/package/1?merge_products=true").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sale", Matchers.is(19)));
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/package/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteByIdFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/package/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void registerNewPackage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(salesPackageDTO);

        Mockito.when(salesPackageService.addNewSalesPackage( salesPackage2 )).thenReturn(salesPackage2);

        mockMvc.perform(MockMvcRequestBuilders.post("/package").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sale", Matchers.is(15)));
    }
}