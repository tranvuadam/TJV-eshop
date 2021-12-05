package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.ProductService;
import cz.cvut.fit.tjv.Eshop.business.SalesPackageService;
import cz.cvut.fit.tjv.Eshop.converter.ProductConverter;
import cz.cvut.fit.tjv.Eshop.converter.SalesPackageConverter;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping(path = "package")
public class SalesPackageController {

    @Autowired
    private SalesPackageService salesPackageService;

    @Autowired
    private ProductService productService;

    @GetMapping(path = "")
    public Collection<SalesPackageDTO> getSalesPackages(){
        return SalesPackageConverter.fromModelMany(salesPackageService.getSalesPackages());
    }

    @GetMapping(path = "", params = "product_id")
    public Collection<SalesPackageDTO> getSalesPackagesContainingProduct(@RequestParam(name = "product_id") Long productId){
        return SalesPackageConverter.fromModelMany(salesPackageService.getSalesPackagesContainingProduct(productId));
    }

    @GetMapping("/{packageId}")
    public SalesPackageDTO getById(@PathVariable("packageId") Long packageId){
        if(packageId != null){
            SalesPackageDTO salesPackage;
            try {
                salesPackage = SalesPackageConverter.fromModel(salesPackageService.getById(packageId));
            }catch (EntityNotFoundException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
            }
            return salesPackage;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

    }
    @PostMapping("/{packageId}")
    public Object updateById(@PathVariable("packageId") Long packageId,
                             @RequestBody SalesPackageDTO salesPackageDTO,
                             @RequestParam(name = "merge_products", required = false, defaultValue = "false") Boolean mergeProducts){
        if (salesPackageService.exists(packageId) && productService.checkProductsExists(salesPackageDTO.getProducts())){
            return SalesPackageConverter.fromModel(salesPackageService.updateById(packageId, salesPackageDTO, mergeProducts));
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
    }

    @DeleteMapping("/{packageId}")
    public Object deleteById(@PathVariable("packageId") Long packageId){
        if (salesPackageService.exists(packageId)){
            salesPackageService.deleteById(packageId);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new SalesPackageDTO());
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
    }

    @PostMapping("")
    public SalesPackageDTO registerNewPackage(@RequestBody SalesPackageDTO salesPackageDTO){
        SalesPackageDTO newSalesPackageDTO;
        try {
            productService.checkProductsExists(salesPackageDTO.getProducts());
            newSalesPackageDTO = SalesPackageConverter.fromModel(salesPackageService.addNewSalesPackage(
                    SalesPackageConverter.toModel(salesPackageDTO)));
        } catch (IllegalArgumentException e){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id: " + e.getMessage() + " does not exist.");
        }
        return newSalesPackageDTO;
    }

}
