package cz.cvut.fit.tjv.Eshop.dao;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.price <= ?1")
    Collection<Product> findProductWithPriceLowerThan(Integer price);

}
