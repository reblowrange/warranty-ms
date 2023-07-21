package com.warranty.service.dto;

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
    private Long duration;

    private String coverage;

    private Long serviceContact;

    private Boolean isReplaceable;

    @NotNull
    private Instant expiredOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Long getServiceContact() {
        return serviceContact;
    }

    public void setServiceContact(Long serviceContact) {
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
            ", duration=" + getDuration() +
            ", coverage='" + getCoverage() + "'" +
            ", serviceContact=" + getServiceContact() +
            ", isReplaceable='" + getIsReplaceable() + "'" +
            ", expiredOn='" + getExpiredOn() + "'" +
            "}";
    }
}
