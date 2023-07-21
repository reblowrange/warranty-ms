package com.warranty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WarrantyDetails.
 */
@Entity
@Table(name = "warranty_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "coverage")
    private String coverage;

    @Column(name = "service_contact")
    private Long serviceContact;

    @Column(name = "is_replaceable")
    private Boolean isReplaceable;

    @NotNull
    @Column(name = "expired_on", nullable = false)
    private Instant expiredOn;

    @JsonIgnoreProperties(value = { "seller", "warrantyDetails" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "warrantyDetails")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WarrantyDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return this.duration;
    }

    public WarrantyDetails duration(Long duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getCoverage() {
        return this.coverage;
    }

    public WarrantyDetails coverage(String coverage) {
        this.setCoverage(coverage);
        return this;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Long getServiceContact() {
        return this.serviceContact;
    }

    public WarrantyDetails serviceContact(Long serviceContact) {
        this.setServiceContact(serviceContact);
        return this;
    }

    public void setServiceContact(Long serviceContact) {
        this.serviceContact = serviceContact;
    }

    public Boolean getIsReplaceable() {
        return this.isReplaceable;
    }

    public WarrantyDetails isReplaceable(Boolean isReplaceable) {
        this.setIsReplaceable(isReplaceable);
        return this;
    }

    public void setIsReplaceable(Boolean isReplaceable) {
        this.isReplaceable = isReplaceable;
    }

    public Instant getExpiredOn() {
        return this.expiredOn;
    }

    public WarrantyDetails expiredOn(Instant expiredOn) {
        this.setExpiredOn(expiredOn);
        return this;
    }

    public void setExpiredOn(Instant expiredOn) {
        this.expiredOn = expiredOn;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.setWarrantyDetails(null);
        }
        if (product != null) {
            product.setWarrantyDetails(this);
        }
        this.product = product;
    }

    public WarrantyDetails product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarrantyDetails)) {
            return false;
        }
        return id != null && id.equals(((WarrantyDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyDetails{" +
            "id=" + getId() +
            ", duration=" + getDuration() +
            ", coverage='" + getCoverage() + "'" +
            ", serviceContact=" + getServiceContact() +
            ", isReplaceable='" + getIsReplaceable() + "'" +
            ", expiredOn='" + getExpiredOn() + "'" +
            "}";
    }
}
