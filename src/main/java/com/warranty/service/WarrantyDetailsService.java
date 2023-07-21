package com.warranty.service;

import com.warranty.service.dto.WarrantyDetailsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.warranty.domain.WarrantyDetails}.
 */
public interface WarrantyDetailsService {
    /**
     * Save a warrantyDetails.
     *
     * @param warrantyDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    WarrantyDetailsDTO save(WarrantyDetailsDTO warrantyDetailsDTO);

    /**
     * Updates a warrantyDetails.
     *
     * @param warrantyDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    WarrantyDetailsDTO update(WarrantyDetailsDTO warrantyDetailsDTO);

    /**
     * Partially updates a warrantyDetails.
     *
     * @param warrantyDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WarrantyDetailsDTO> partialUpdate(WarrantyDetailsDTO warrantyDetailsDTO);

    /**
     * Get all the warrantyDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WarrantyDetailsDTO> findAll(Pageable pageable);

    /**
     * Get all the WarrantyDetailsDTO where Product is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<WarrantyDetailsDTO> findAllWhereProductIsNull();

    /**
     * Get the "id" warrantyDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WarrantyDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" warrantyDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
