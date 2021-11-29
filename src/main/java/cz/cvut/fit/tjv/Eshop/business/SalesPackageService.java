package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tjv.Eshop.dao.SalesPackageJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Component
public class SalesPackageService {

    @Autowired
    private SalesPackageJpaRepository salesPackageRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    public Collection<SalesPackage> getSalesPackages(){
        return salesPackageRepository.findAll();
    }

    public SalesPackage getById(Long salesPackageId){
        return salesPackageRepository.getById(salesPackageId);
    }

    public boolean exists(Long salesPackageId){
        return salesPackageRepository.existsById(salesPackageId);
    }



    TreeSet<Product> mergeProducts (Set<Product> productListA, Set<Product> productListB){
        TreeSet<Product> mergedSet = new TreeSet<Product>(productListA);
        mergedSet.addAll(productListB);
        return mergedSet;
    }


    @Transactional
    public void deleteById(Long salesPackageId){
        salesPackageRepository.deleteById(salesPackageId);
    }

    @Transactional
    public SalesPackage addNewSalesPackage(SalesPackage salesPackage){
        return salesPackageRepository.save(salesPackage);
    }

    @Transactional
    public SalesPackage updateById(Long packageId, SalesPackageDTO salesPackageDTO, Boolean mergeProducts) {
        SalesPackage salesPackage = salesPackageRepository.getById(packageId);
        if(salesPackageDTO.getProducts() != null)
            if(mergeProducts)
                salesPackage.setProducts(mergeProducts(salesPackageDTO.getProducts(), salesPackage.getProducts()));
            else
                salesPackage.setProducts(salesPackageDTO.getProducts());

        if(salesPackageDTO.getSale() != null)
            salesPackage.setSale(salesPackageDTO.getSale());
        return salesPackageRepository.save(salesPackage);
    }

    public Collection<SalesPackage> getSalesPackagesContainingProduct(Long productId) {
        return salesPackageRepository.findByProductsIdInSalesPackage(productId);
    }
}
