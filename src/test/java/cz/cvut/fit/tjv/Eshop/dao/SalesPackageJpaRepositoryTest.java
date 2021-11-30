package cz.cvut.fit.tjv.Eshop.dao;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SalesPackageJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private SalesPackageJpaRepository salesPackageJpaRepository;


    @Test
    void findByProductsIdInSalesPackage() {
        Product product1 = new Product("product1", 145);
        Product product2 = new Product("product2", 140);
        Product product3 = new Product("product3", 135);
        Product product4 = new Product("product4", 111);
        productJpaRepository.save(product1);
        productJpaRepository.save(product2);
        productJpaRepository.save(product3);
        productJpaRepository.save(product4);

        SalesPackage salesPackage1 = new SalesPackage(new HashSet<>(Arrays.asList(product1, product2, product3)), 15);
        SalesPackage salesPackage2 = new SalesPackage(new HashSet<>(Arrays.asList(product1, product3, product4)), 19);
        SalesPackage salesPackage3 = new SalesPackage(new HashSet<>(Arrays.asList(product2, product3)), 21);
        salesPackageJpaRepository.save(salesPackage1);
        salesPackageJpaRepository.save(salesPackage2);
        salesPackageJpaRepository.save(salesPackage3);

        Assertions.assertEquals(productJpaRepository.getById(1L), product1);
        Collection<SalesPackage> salesPackagesReturned = salesPackageJpaRepository.findByProductsIdInSalesPackage(1L);
        Assertions.assertEquals(2, salesPackagesReturned.size());
        Assertions.assertTrue(salesPackagesReturned.contains(salesPackage1) && salesPackagesReturned.contains(salesPackage2));

        Assertions.assertEquals(productJpaRepository.getById(4L), product4);
        Collection<SalesPackage> salesPackagesReturned2 = salesPackageJpaRepository.findByProductsIdInSalesPackage(4L);
        Assertions.assertEquals(1, salesPackagesReturned2.size());
        Assertions.assertTrue(salesPackagesReturned2.contains(salesPackage2));

    }
}