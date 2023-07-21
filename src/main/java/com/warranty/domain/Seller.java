package com.warranty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Seller.
 */
@Entity
@Table(name = "seller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "seller", nullable = false)
    private String seller;

    @Column(name = "address")
    private String address;

    @Column(name = "contact")
    private Long contact;

    @JsonIgnoreProperties(value = { "seller", "warrantyDetails" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "seller")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Seller id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeller() {
        return this.seller;
    }

    public Seller seller(String seller) {
        this.setSeller(seller);
        return this;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getAddress() {
        return this.address;
    }

    public Seller address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContact() {
        return this.contact;
    }

    public Seller contact(Long contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.setSeller(null);
        }
        if (product != null) {
            product.setSeller(this);
        }
        this.product = product;
    }

    public Seller product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seller)) {
            return false;
        }
        return id != null && id.equals(((Seller) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seller{" +
            "id=" + getId() +
            ", seller='" + getSeller() + "'" +
            ", address='" + getAddress() + "'" +
            ", contact=" + getContact() +
            "}";
    }
}
