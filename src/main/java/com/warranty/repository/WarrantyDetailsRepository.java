package com.warranty.repository;

import com.warranty.domain.WarrantyDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WarrantyDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarrantyDetailsRepository extends JpaRepository<WarrantyDetails, Long> {}
