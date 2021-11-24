package cz.cvut.fit.tjv.Eshop.dto;

public class ProductDTO {
    private Long id;
    public String name;
    public Integer price;

    public ProductDTO() {
    }

    public ProductDTO(String name, Integer price, Long id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }
    public Long getId() {
        return id;
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
