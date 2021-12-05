package cz.cvut.fit.tjv.social_network.web.model;

public class ProductWebModel extends ProductDTO{
    private boolean error;

    public ProductWebModel() {
    }

    public ProductWebModel(boolean error, ProductDTO productDTO) {
        super(productDTO.getName(), productDTO.getPrice(), productDTO.getId());
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
