package cz.cvut.fit.tjv.Eshop.converter;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;

import java.util.ArrayList;
import java.util.Collection;

public class ProductConverter {
    public static Product toModel(ProductDTO productDTO) {
        return new Product(productDTO.getName(), productDTO.getPrice());
    }
    public static ProductDTO fromModel(Product product) {
        return new ProductDTO(product.getName(), product.getPrice(), product.getId());
    }
    public static Collection<Product> toModelMany(Collection<ProductDTO> productDTOs){
        Collection<Product> products = new ArrayList<>();
        productDTOs.forEach(p -> products.add(toModel(p)));
        return products;
    }
    public static Collection<ProductDTO> fromModelMany(Collection<Product> products){
        Collection<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(p -> productDTOs.add(fromModel(p)));
        return productDTOs;
    }
}
