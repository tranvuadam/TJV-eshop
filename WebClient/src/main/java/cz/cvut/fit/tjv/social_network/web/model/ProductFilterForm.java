package cz.cvut.fit.tjv.social_network.web.model;

public class ProductFilterForm {
    private Long id;

    private Integer highestPrice;

    public ProductFilterForm(){

    }

    public Integer getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Integer highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
