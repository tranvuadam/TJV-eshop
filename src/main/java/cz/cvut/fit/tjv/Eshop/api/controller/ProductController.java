package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.ProductService;
import cz.cvut.fit.tjv.Eshop.converter.ProductConverter;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * To use product service, start the server and send requests to localhost:8080/product
 */
@RestController
@RequestMapping(path = "product")
public class ProductController {
    @Autowired
    private ProductService productService;
    /**
     * Send get request to localhost:8080/product/view_all to view all products
     */
    @GetMapping(path = "/")
    public Collection<ProductDTO> getProducts(){

        return ProductConverter.fromModelMany(productService.getProducts());
    }
    /**
     * Send get request to localhost:8080/product/{id} to view a product by ID
     */
    @GetMapping("/{productId}")
    public Object getById(@PathVariable("productId") Long productId){
        ProductDTO product;
        try {
            product = ProductConverter.fromModel(productService.getById(productId));
        }catch (IllegalArgumentException e){
            return HttpStatus.NOT_FOUND;
        }
        return product;
    }
    /**
     * Send post request with a request body to localhost:8080/product/new_product to create a new product
     * Example:
     *      POST localhost:8080/product/new_product
     *      {
     *          "name": "productExample",
     *          "price": 100
     *      }
     */
    @PostMapping("/")
    public HttpStatus registerNewProduct(@RequestBody ProductDTO productDTO){
        try {
            productService.addNewProduct(ProductConverter.toModel(productDTO));
        } catch (IllegalArgumentException e){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }
}
