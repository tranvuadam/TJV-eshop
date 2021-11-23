package cz.cvut.fit.tjv.Eshop.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
public class SalesPackage {

    @Id
    @GeneratedValue
            //(strategy = GenerationType.SEQUENCE, generator = "sales_package_id_sequence")
            //@SequenceGenerator(name="sales_package_id_sequence", sequenceName = "sales_package_id_sequence", allocationSize = 1)
    private Long id;

    @ManyToMany
    private Set<Product> products = new HashSet<>();

    private Integer sale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesPackage that = (SalesPackage) o;
        return Objects.equals(products, that.products) && Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, sale);
    }

    @Override
    public String toString() {
        return "SalesPackage{" +
                "products=" + products +
                ", sale=" + sale +
                '}';
    }

    public SalesPackage(HashSet<Product> products, Integer sale) {
        this.products = products;
        this.sale = sale;
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
