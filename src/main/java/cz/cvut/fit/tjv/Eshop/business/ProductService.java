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
        return productRepository.getById(productId);
    }

    public boolean exists(Long productId){
        return productRepository.existsById(productId);
    }

    @Transactional
    public void deleteById(Long productId){
        productRepository.deleteById(productId);
    }

    @Transactional
    public Product addNewProduct(Product product){
        return productRepository.save(product);
    }

}