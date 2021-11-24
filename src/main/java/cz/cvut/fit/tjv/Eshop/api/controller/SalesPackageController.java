package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.SalesPackageService;
import cz.cvut.fit.tjv.Eshop.converter.SalesPackageConverter;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(path = "/")
    public Collection<SalesPackageDTO> getSalesPackages(){
        return SalesPackageConverter.fromModelMany(salesPackageService.getSalesPackages());
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

    @DeleteMapping("/{packageId}")
    public Object deleteById(@PathVariable("packageId") Long packageId){
        if (salesPackageService.exists(packageId)){
            salesPackageService.deleteById(packageId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted package with ID: " + packageId);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
    }

    @PostMapping("/")
    public SalesPackage registerNewPackage(@RequestBody SalesPackageDTO salesPackageDTO){
        SalesPackage salesPackage;
        try {
            salesPackage = salesPackageService.addNewSalesPackage(SalesPackageConverter.toModel(salesPackageDTO));
        } catch (IllegalArgumentException e){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id: " + e.getMessage() + " does not exist.");
        }
        return salesPackage;
    }

}
