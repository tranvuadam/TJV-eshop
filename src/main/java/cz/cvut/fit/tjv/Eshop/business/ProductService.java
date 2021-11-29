package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

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

    public boolean checkProductsExists(Set<Product> products){
        for (Product p: products) {
            if(!productRepository.existsById(p.getId())){
                return false;
            }
        }
        return true;
    }

    @Transactional
    public void deleteById(Long productId){
        productRepository.deleteById(productId);
    }

    @Transactional
    public Product addNewProduct(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public Product updateById(Long productId, ProductDTO productDTO) {
        Product product = productRepository.getById(productId);
        if(productDTO.getName() != null)
            product.setName(productDTO.getName());

        if(productDTO.getPrice() != null)
            product.setPrice(productDTO.getPrice());

        return productRepository.save(product);
    }

    public Collection<Product> getProductsWithPriceLowerThan(Integer price) {
        return productRepository.findProductWithPriceLowerThan(price);
    }
}
