package cz.cvut.fit.tjv.Eshop.converter;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;

import java.util.ArrayList;
import java.util.Collection;

public class SalesPackageConverter {
    public static SalesPackage toModel(SalesPackageDTO salesPackageDTO) {
        return new SalesPackage(salesPackageDTO.getProducts(), salesPackageDTO.getSale());
    }
    public static SalesPackageDTO fromModel(SalesPackage salesPackage) {
        return new SalesPackageDTO(salesPackage.getProducts(), salesPackage.getSale(), salesPackage.getId());
    }
    public static Collection<SalesPackage> toModelMany(Collection<SalesPackageDTO> salesPackageDTOS){
        Collection<SalesPackage> packages = new ArrayList<>();
        salesPackageDTOS.forEach(p -> packages.add(toModel(p)));
        return packages;
    }
    public static Collection<SalesPackageDTO> fromModelMany(Collection<SalesPackage> salesPackages){
        Collection<SalesPackageDTO> packageDTOs = new ArrayList<>();
        salesPackages.forEach(p -> packageDTOs.add(fromModel(p)));
        return packageDTOs;
    }
}
