package cz.cvut.fit.tjv.Eshop.domain;

import java.util.Objects;

public class ProductReview {
    private User reviewer;
    private Product product;
    private Integer rating;
    private String reviewText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReview that = (ProductReview) o;
        return Objects.equals(reviewer, that.reviewer) && Objects.equals(product, that.product) && Objects.equals(rating, that.rating) && Objects.equals(reviewText, that.reviewText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewer, product, rating, reviewText);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "reviewer=" + reviewer +
                ", product=" + product +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }

    public ProductReview(User reviewer, Product product, Integer rating, String reviewText) {
        this.reviewer = reviewer;
        this.product = product;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
