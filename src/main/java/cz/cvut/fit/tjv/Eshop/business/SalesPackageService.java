package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.ProductJpaRepository;
import cz.cvut.fit.tjv.Eshop.dao.SalesPackageJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;

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

    @Transactional
    public void deleteById(Long salesPackageId){
        salesPackageRepository.deleteById(salesPackageId);
    }

    @Transactional
    public SalesPackage addNewSalesPackage(SalesPackage salesPackage){
        for (Product p: salesPackage.getProducts()) {
            if(!productJpaRepository.existsById(p.getId())){
                throw new IllegalArgumentException(p.getId().toString());
            }
        }
        return salesPackageRepository.save(salesPackage);
    }

}
