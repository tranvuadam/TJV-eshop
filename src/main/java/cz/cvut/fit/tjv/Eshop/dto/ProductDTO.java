package cz.cvut.fit.tjv.Eshop.dto;

public class ProductDTO {
    public String name;
    public Integer price;

    public ProductDTO() {
    }

    public ProductDTO(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
