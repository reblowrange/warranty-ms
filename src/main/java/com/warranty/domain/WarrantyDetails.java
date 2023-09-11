package com.warranty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.warranty.domain.enumeration.DurationType;
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
    @Column(name = "bill_date", nullable = false)
    private Instant billDate;

    @Column(name = "bill_number")
    private String billNumber;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "duration_type", nullable = false)
    private DurationType durationType;

    @Pattern(regexp = "^[0-9]{10}$")
    @Column(name = "service_contact")
    private String serviceContact;

    @Column(name = "is_replaceable")
    private Boolean isReplaceable;

    @NotNull
    @Column(name = "expired_on", nullable = false)
    private Instant expiredOn;

    @Lob
    @Column(name = "bill")
    private byte[] bill;

    @Column(name = "bill_content_type")
    private String billContentType;

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

    public Instant getBillDate() {
        return this.billDate;
    }

    public WarrantyDetails billDate(Instant billDate) {
        this.setBillDate(billDate);
        return this;
    }

    public void setBillDate(Instant billDate) {
        this.billDate = billDate;
    }

    public String getBillNumber() {
        return this.billNumber;
    }

    public WarrantyDetails billNumber(String billNumber) {
        this.setBillNumber(billNumber);
        return this;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public WarrantyDetails duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public DurationType getDurationType() {
        return this.durationType;
    }

    public WarrantyDetails durationType(DurationType durationType) {
        this.setDurationType(durationType);
        return this;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public String getServiceContact() {
        return this.serviceContact;
    }

    public WarrantyDetails serviceContact(String serviceContact) {
        this.setServiceContact(serviceContact);
        return this;
    }

    public void setServiceContact(String serviceContact) {
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

    public byte[] getBill() {
        return this.bill;
    }

    public WarrantyDetails bill(byte[] bill) {
        this.setBill(bill);
        return this;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    public String getBillContentType() {
        return this.billContentType;
    }

    public WarrantyDetails billContentType(String billContentType) {
        this.billContentType = billContentType;
        return this;
    }

    public void setBillContentType(String billContentType) {
        this.billContentType = billContentType;
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
            ", billDate='" + getBillDate() + "'" +
            ", billNumber='" + getBillNumber() + "'" +
            ", duration=" + getDuration() +
            ", durationType='" + getDurationType() + "'" +
            ", serviceContact='" + getServiceContact() + "'" +
            ", isReplaceable='" + getIsReplaceable() + "'" +
            ", expiredOn='" + getExpiredOn() + "'" +
            ", bill='" + getBill() + "'" +
            ", billContentType='" + getBillContentType() + "'" +
            "}";
    }
}
