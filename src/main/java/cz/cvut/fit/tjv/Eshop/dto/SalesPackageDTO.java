package cz.cvut.fit.tjv.Eshop.dto;

import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.SalesPackage;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SalesPackageDTO {

    private Long id;

    private Set<Product> products = new HashSet<>();

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

    public SalesPackageDTO(Set<Product> products, Integer sale, Long id) {
        this.products = products;
        this.sale = sale;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
}
