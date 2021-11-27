package cz.cvut.fit.tjv.Eshop.dao;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SalesPackageJpaRepository extends JpaRepository<SalesPackage, Long> {
    @Query("SELECT s FROM SalesPackage s JOIN s.products p WHERE p.id = ?1")
    Collection<SalesPackage> findByProductsIdInSalesPackage(Long productId);

    //OR

    /* Using property expression
        Collection<SalesPackage> findByProducts_Id(Long productId);
    */
}
