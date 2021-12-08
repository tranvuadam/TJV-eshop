package cz.cvut.fit.tjv.social_network.web.ui;

import cz.cvut.fit.tjv.social_network.web.data.ProductClient;
import cz.cvut.fit.tjv.social_network.web.model.ProductDTO;
import cz.cvut.fit.tjv.social_network.web.model.ProductFilterForm;
import cz.cvut.fit.tjv.social_network.web.model.ProductWebModel;
import cz.cvut.fit.tjv.social_network.web.model.SalesPackageFilterForm;
import cz.cvut.fit.tjv.social_network.web.model.UserDTO;
import cz.cvut.fit.tjv.social_network.web.model.UserWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/product")
public class ProductWebController {
    private final ProductClient productClient;

    public ProductWebController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("products", productClient.readAll());
        model.addAttribute("productFilterForm", new ProductFilterForm());
        return "products";
    }

    @GetMapping("/filter")
    public String listWithPriceLowerThan( Model model, @ModelAttribute ProductFilterForm productFilterForm) {
        if(productFilterForm.getHighestPrice() == null)
            model.addAttribute("products", productClient.readAll());
        else
            model.addAttribute("products", productClient.readAllWithPriceLowerThan(productFilterForm.getHighestPrice()));

        model.addAttribute("productFilterForm", new ProductFilterForm());
        return "products";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam(name = "id") Long Id, Model model) {
        model.addAttribute("productDto", productClient.readById(Id));
        return "productEdit";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam(name = "id") Long Id, Model model) {
        model.addAttribute("productDto", productClient.delete(Id));
        return "redirect:/product";
    }

    @PostMapping("/edit")
    public String editProductSubmit(@ModelAttribute ProductDTO productDTO, Model model) {
        model.addAttribute("productDto", productClient.update(productDTO));
        return "productEdit";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("productWebModel", new ProductWebModel());
        return "productAdd";
    }

    @PostMapping("/add")
    public String addProductSubmit(@ModelAttribute ProductWebModel productWebModel, Model model) {
        model.addAttribute("productWebModel",
                productClient.create(productWebModel).onErrorResume(WebClientResponseException.BadGateway.class, e -> Mono.just(new ProductWebModel(true, productWebModel))));
        return "productAdd";
    }
}
