package cz.cvut.fit.tjv.Eshop.dao;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
