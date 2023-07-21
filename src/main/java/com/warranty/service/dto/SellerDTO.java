package com.warranty.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.warranty.domain.Seller} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellerDTO implements Serializable {

    private Long id;

    @NotNull
    private String seller;

    private String address;

    private Long contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellerDTO)) {
            return false;
        }

        SellerDTO sellerDTO = (SellerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sellerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellerDTO{" +
            "id=" + getId() +
            ", seller='" + getSeller() + "'" +
            ", address='" + getAddress() + "'" +
            ", contact=" + getContact() +
            "}";
    }
}
