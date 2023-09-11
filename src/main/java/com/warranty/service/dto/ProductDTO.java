package com.warranty.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.warranty.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String product;

    @NotNull
    private Long paidAmount;

    private String description;

    private SellerDTO seller;

    private WarrantyDetailsDTO warrantyDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public WarrantyDetailsDTO getWarrantyDetails() {
        return warrantyDetails;
    }

    public void setWarrantyDetails(WarrantyDetailsDTO warrantyDetails) {
        this.warrantyDetails = warrantyDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", product='" + getProduct() + "'" +
            ", paidAmount=" + getPaidAmount() +
            ", description='" + getDescription() + "'" +
            ", seller=" + getSeller() +
            ", warrantyDetails=" + getWarrantyDetails() +
            "}";
    }
}
