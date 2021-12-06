package cz.cvut.fit.tjv.social_network.web.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SalesPackageWebModel extends SalesPackageDTO {

    private String idString;

    private Set<ProductDTO> newProducts = new HashSet<>();

    private Integer sale;

    private boolean mergeProducts = false;

    private boolean numberFormatError = false;

    private boolean productNotFoundError = false;

    public SalesPackageWebModel() {
    }

    public SalesPackageWebModel(boolean numberFormatError, boolean productNotFoundError) {
        this.numberFormatError = numberFormatError;
        this.productNotFoundError = productNotFoundError;
    }

    public SalesPackageWebModel(boolean numberFormatError, boolean productNotFoundError, SalesPackageDTO salesPackageDTO) {
        super(salesPackageDTO.getProducts(), salesPackageDTO.getSale(), salesPackageDTO.getId());
        this.numberFormatError = numberFormatError;
        this.productNotFoundError = productNotFoundError;
    }

    public ArrayList<Long> parseIds(){
        String[] items = idString.split(" ");
        ArrayList<Long> ids = new ArrayList<>();
        for (String item : items) {
            try {
                ids.add(Long.parseLong(item));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid format");
            }
        }
        idString = "";
        return ids;
    }

    public void convertProductIdToProducts( ){

        for (Long productId :  parseIds()) {
            ProductDTO productDTO = new ProductDTO(null, null, productId);
            newProducts.add(productDTO);
        }
    }

    @Override
    public String toString() {
        return "SalesPackageWebModel{" +
                "idString='" + idString + '\'' +
                ", newProducts=" + newProducts +
                ", sale=" + sale +
                '}';
    }

    public boolean isNumberFormatError() {
        return numberFormatError;
    }

    public void setNumberFormatError(boolean numberFormatError) {
        this.numberFormatError = numberFormatError;
    }

    public boolean isProductNotFoundError() {
        return productNotFoundError;
    }

    public void setProductNotFoundError(boolean productNotFoundError) {
        this.productNotFoundError = productNotFoundError;
    }

    public Set<ProductDTO> getNewProducts() {
        return newProducts;
    }

    public void setNewProducts(Set<ProductDTO> newProducts) {
        this.newProducts = newProducts;
    }

    public boolean isMergeProducts() {
        return mergeProducts;
    }

    public void setMergeProducts(boolean mergeProducts) {
        this.mergeProducts = mergeProducts;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) { this.idString = idString; }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

}
