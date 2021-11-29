package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.SalesPackageJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class SalesPackageServiceTest {

    @Autowired
    private SalesPackageService salesPackageService;

    @MockBean
    private SalesPackageJpaRepository salesPackageJpaRepository;

    @Test
    void updateByIdNoOverwrite() {
        Product product1 = new Product("product1", 145);
        Product product2 = new Product("product2", 145);
        Set<Product> products = new HashSet<Product>(Arrays.asList(product1, product2));

        SalesPackage salesPackage = new SalesPackage(products, 15);

        SalesPackageDTO salesPackageDTO = new SalesPackageDTO(null, null, 1L);

        Mockito.when(salesPackageService.getById(1L)).thenReturn(salesPackage);
        Mockito.when(salesPackageJpaRepository.save(salesPackage)).thenReturn(salesPackage);
        SalesPackage salesPackageReturned = salesPackageService.updateById(1L, salesPackageDTO, false);
        Assertions.assertEquals(salesPackage, salesPackageReturned);
    }

    @Test
    void updateByIdOverwrite() {
        Product product1 = new Product("product1", 145);
        Product product2 = new Product("product2", 145);
        Product product3 = new Product("product3", 145);

        Set<Product> products = new HashSet<Product>(Arrays.asList(product1, product2));

        Set<Product> products2 = new HashSet<Product>(Arrays.asList(product1, product2, product3));

        SalesPackage salesPackage = new SalesPackage(products, 15);

        SalesPackageDTO salesPackageDTO = new SalesPackageDTO(products2, 250, 1L);

        Mockito.when(salesPackageService.getById(1L)).thenReturn(salesPackage);
        Mockito.when(salesPackageJpaRepository.save(salesPackage)).thenReturn(salesPackage);
        SalesPackage salesPackageReturned = salesPackageService.updateById(1L, salesPackageDTO, false);
        Assertions.assertEquals(products2, salesPackageReturned.getProducts());
        Assertions.assertEquals(250, salesPackageReturned.getSale());
    }
}