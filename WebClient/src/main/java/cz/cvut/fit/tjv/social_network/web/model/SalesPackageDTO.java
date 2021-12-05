package cz.cvut.fit.tjv.social_network.web.model;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SalesPackageDTO {

    private Long id;

    private Set<ProductDTO> products = new HashSet<>();

    private Integer sale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesPackageDTO that = (SalesPackageDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(products, that.products) && Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, sale);
    }

    @Override
    public String toString() {
        return "SalesPackage{" +
                "products=" + products +
                ", sale=" + sale +
                '}';
    }

    public SalesPackageDTO(Set<ProductDTO> products, Integer sale, Long id) {
        this.products = products;
        this.sale = sale;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
}
