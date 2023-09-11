package com.warranty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "product", nullable = false)
    private String product;

    @NotNull
    @Column(name = "paid_amount", nullable = false)
    private Long paidAmount;

    @Column(name = "description")
    private String description;

    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Seller seller;

    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private WarrantyDetails warrantyDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return this.product;
    }

    public Product product(String product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getPaidAmount() {
        return this.paidAmount;
    }

    public Product paidAmount(Long paidAmount) {
        this.setPaidAmount(paidAmount);
        return this;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Product seller(Seller seller) {
        this.setSeller(seller);
        return this;
    }

    public WarrantyDetails getWarrantyDetails() {
        return this.warrantyDetails;
    }

    public void setWarrantyDetails(WarrantyDetails warrantyDetails) {
        this.warrantyDetails = warrantyDetails;
    }

    public Product warrantyDetails(WarrantyDetails warrantyDetails) {
        this.setWarrantyDetails(warrantyDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", product='" + getProduct() + "'" +
            ", paidAmount=" + getPaidAmount() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
