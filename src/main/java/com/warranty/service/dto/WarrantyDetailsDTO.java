package com.warranty.service.dto;

import com.warranty.domain.enumeration.DurationType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.warranty.domain.WarrantyDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant billDate;

    private String billNumber;

    @NotNull
    private Integer duration;

    @NotNull
    private DurationType durationType;

    @Pattern(regexp = "^[0-9]{10}$")
    private String serviceContact;

    private Boolean isReplaceable;

    @NotNull
    private Instant expiredOn;

    @Lob
    private byte[] bill;

    private String billContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBillDate() {
        return billDate;
    }

    public void setBillDate(Instant billDate) {
        this.billDate = billDate;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public String getServiceContact() {
        return serviceContact;
    }

    public void setServiceContact(String serviceContact) {
        this.serviceContact = serviceContact;
    }

    public Boolean getIsReplaceable() {
        return isReplaceable;
    }

    public void setIsReplaceable(Boolean isReplaceable) {
        this.isReplaceable = isReplaceable;
    }

    public Instant getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(Instant expiredOn) {
        this.expiredOn = expiredOn;
    }

    public byte[] getBill() {
        return bill;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    public String getBillContentType() {
        return billContentType;
    }

    public void setBillContentType(String billContentType) {
        this.billContentType = billContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarrantyDetailsDTO)) {
            return false;
        }

        WarrantyDetailsDTO warrantyDetailsDTO = (WarrantyDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, warrantyDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyDetailsDTO{" +
            "id=" + getId() +
            ", billDate='" + getBillDate() + "'" +
            ", billNumber='" + getBillNumber() + "'" +
            ", duration=" + getDuration() +
            ", durationType='" + getDurationType() + "'" +
            ", serviceContact='" + getServiceContact() + "'" +
            ", isReplaceable='" + getIsReplaceable() + "'" +
            ", expiredOn='" + getExpiredOn() + "'" +
            ", bill='" + getBill() + "'" +
            "}";
    }
}
