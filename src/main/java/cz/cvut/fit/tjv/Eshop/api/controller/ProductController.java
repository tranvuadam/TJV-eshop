package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.ProductService;
import cz.cvut.fit.tjv.Eshop.converter.ProductConverter;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

/**
 * To use product service, start the server and send requests to localhost:8080/product
 */
@RestController
@RequestMapping(path = "product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/")
    public Collection<ProductDTO> getProducts(){

        return ProductConverter.fromModelMany(productService.getProducts());
    }

    @GetMapping(path = "")
    public Collection<ProductDTO> getProductsWithPriceLowerThan(@RequestParam(name = "highest_price") Integer price){

        return ProductConverter.fromModelMany(productService.getProductsWithPriceLowerThan(price));
    }

    @GetMapping("/{productId}")
    public Object getById(@PathVariable("productId") Long productId){
        ProductDTO product;
        try {
            product = ProductConverter.fromModel(productService.getById(productId));
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        return product;
    }

    @DeleteMapping("/{productId}")
    public Object deleteById(@PathVariable("productId") Long productId){
        if (productService.exists(productId)){
            productService.deleteById(productId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted product with ID: " + productId);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
    }

    @PostMapping("/{productId}")
    public ProductDTO updateById(@PathVariable("productId") Long productId, @RequestBody ProductDTO productDTO){
        if (productService.exists(productId)){
            return ProductConverter.fromModel(productService.updateById(productId, productDTO));
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
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
    public ProductDTO registerNewProduct(@RequestBody ProductDTO productDTO){
        ProductDTO newProductDTO;
        try {
            newProductDTO = ProductConverter.fromModel(productService.addNewProduct(ProductConverter.toModel(productDTO)));
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return newProductDTO;
    }
}
