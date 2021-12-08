package cz.cvut.fit.tjv.social_network.web.ui;

import cz.cvut.fit.tjv.social_network.web.data.ProductClient;
import cz.cvut.fit.tjv.social_network.web.data.SalesPackageClient;
import cz.cvut.fit.tjv.social_network.web.model.ProductWebModel;
import cz.cvut.fit.tjv.social_network.web.model.SalesPackageFilterForm;
import cz.cvut.fit.tjv.social_network.web.model.SalesPackageWebModel;
import cz.cvut.fit.tjv.social_network.web.model.SalesPackageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/package")
public class SalesPackageWebController {

    private final SalesPackageClient salesPackageClient;
    private final ProductClient productClient;

    public SalesPackageWebController(SalesPackageClient salesPackageClient, ProductClient productClient) {
        this.salesPackageClient = salesPackageClient;
        this.productClient = productClient;
    }


    @GetMapping
    public String list(Model model) {
        model.addAttribute("packages", salesPackageClient.readAll());
        model.addAttribute("packageFilterForm", new SalesPackageFilterForm());
        return "packages";
    }

    @GetMapping("/filter")
    public String list(@ModelAttribute SalesPackageFilterForm packageFilterForm, Model model) {
        if(packageFilterForm.getId() == null)
            model.addAttribute("packages", salesPackageClient.readAll());
        else
            model.addAttribute("packages", salesPackageClient.getSalesPackagesContainingProduct(packageFilterForm.getId()));

        model.addAttribute("packageFilterForm", new SalesPackageFilterForm());
        return "packages";
    }

    /*@GetMapping(params = "highest_price")
    public String listWithPriceLowerThan(@RequestParam(name = "highest_price") Integer price, Model model) {
        model.addAttribute("products", productClient.readAllWithPriceLowerThan(price));
        return "products";
    }*/

    @GetMapping("/edit")
    public String editPackage(@RequestParam(name = "id") Long Id, Model model) {
        model.addAttribute("packageForm", salesPackageClient.readById(Id));
        model.addAttribute("products", productClient.readAll());
        return "packageEdit";
    }

    @PostMapping("/delete")
    public String deletePackage(@RequestParam(name = "id") Long Id, Model model) {
        model.addAttribute("packageDto", salesPackageClient.delete(Id));
        return "redirect:/package";
    }

    @PostMapping("/edit")
    public String editPackageSubmit(@ModelAttribute SalesPackageWebModel packageDto, Model model) {
        model.addAttribute("products", productClient.readAll());
        try{
            packageDto.convertProductIdToProducts();
            SalesPackageDTO updatedPackage = new SalesPackageDTO(packageDto.getNewProducts(), packageDto.getSale(), packageDto.getId());
            model.addAttribute("packageForm", salesPackageClient.update(updatedPackage, packageDto.isMergeProducts()).
                    onErrorResume(WebClientResponseException.NotFound.class,
                            e -> Mono.just(new SalesPackageWebModel( false, true, packageDto))));

        }catch (NumberFormatException e){
            model.addAttribute("packageForm", new SalesPackageWebModel( true, false, packageDto));
        }
        return "packageEdit";
    }

    @GetMapping("/add")
    public String addPackage(Model model) {
        model.addAttribute("packageForm", new SalesPackageWebModel());
        model.addAttribute("products", productClient.readAll());
        return "packageAdd";
    }

    @PostMapping(value = "/add")
    public String addProductSubmit(@ModelAttribute SalesPackageWebModel packageForm,
                                   Model model) {
        model.addAttribute("products", productClient.readAll());
        try{
            packageForm.convertProductIdToProducts();
            SalesPackageDTO newPackage = new SalesPackageDTO(packageForm.getNewProducts(), packageForm.getSale(), null);
            model.addAttribute("packageForm", salesPackageClient.create(newPackage)
                    .onErrorResume(WebClientResponseException.NotFound.class, e -> Mono.just(new SalesPackageWebModel(false, true))));

        }catch (NumberFormatException e){
            model.addAttribute("packageForm", new SalesPackageWebModel( true, false));
        }
        return "packageAdd";

    }
}
