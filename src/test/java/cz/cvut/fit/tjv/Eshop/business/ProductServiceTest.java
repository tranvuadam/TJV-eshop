package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductJpaRepository productJpaRepository;


    @Test
    void updateByIdNoOverwrite() {
        Product product = new Product("product1", 145);

        ProductDTO productDTO = new ProductDTO(null, null, 1L);

        Mockito.when(productJpaRepository.getById(1L)).thenReturn(product);
        Mockito.when(productJpaRepository.save(product)).thenReturn(product);
        Product productReturned = productService.updateById(1L, productDTO);
        Assertions.assertEquals(product, productReturned);
    }

    @Test
    void updateByIdOverwrite() {
        Product product = new Product("product1", 145);

        ProductDTO productDTO = new ProductDTO("overwrite", null, 1L);

        Mockito.when(productService.getById(1L)).thenReturn(product);
        Mockito.when(productJpaRepository.save(product)).thenReturn(product);
        Product productReturned = productService.updateById(1L, productDTO);
        Assertions.assertEquals("overwrite", productReturned.getName());
    }


}