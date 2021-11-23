package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;

@Component
public class ProductService {

    @Autowired
    private ProductJpaRepository productRepository;

    public Collection<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getById(Long productId){
        Product product;
        try {
            product = productRepository.getById(productId);
        }
        catch (EntityNotFoundException e){
            throw new IllegalStateException("Product with Id " + productId + " does not exist.");
        }
        return product;
    }

    @Transactional
    public void addNewProduct(Product product){
        productRepository.save(product);
    }

}
