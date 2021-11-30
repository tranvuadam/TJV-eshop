package cz.cvut.fit.tjv.Eshop.dao;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    void findProductWithPriceLowerThan() {
        Product product1 = new Product("product1", 145);
        Product product2 = new Product("product2", 140);
        Product product3 = new Product("product3", 135);
        Product product4 = new Product("product4", 111);
        productJpaRepository.save(product1);
        productJpaRepository.save(product2);
        productJpaRepository.save(product3);
        productJpaRepository.save(product4);
        Collection<Product> productsReturned = productJpaRepository.findProductWithPriceLowerThan(140);
        Assertions.assertEquals(3, productsReturned.size());
        Assertions.assertTrue(productsReturned.contains(product2));
        Assertions.assertTrue(productsReturned.contains(product3));
        Assertions.assertTrue(productsReturned.contains(product4));

    }
}