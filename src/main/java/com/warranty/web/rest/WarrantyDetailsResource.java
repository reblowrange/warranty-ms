package com.warranty.web.rest;

import com.warranty.repository.WarrantyDetailsRepository;
import com.warranty.service.WarrantyDetailsService;
import com.warranty.service.dto.WarrantyDetailsDTO;
import com.warranty.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.warranty.domain.WarrantyDetails}.
 */
@RestController
@RequestMapping("/api")
public class WarrantyDetailsResource {

    private final Logger log = LoggerFactory.getLogger(WarrantyDetailsResource.class);

    private static final String ENTITY_NAME = "warrantyMsWarrantyDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarrantyDetailsService warrantyDetailsService;

    private final WarrantyDetailsRepository warrantyDetailsRepository;

    public WarrantyDetailsResource(WarrantyDetailsService warrantyDetailsService, WarrantyDetailsRepository warrantyDetailsRepository) {
        this.warrantyDetailsService = warrantyDetailsService;
        this.warrantyDetailsRepository = warrantyDetailsRepository;
    }

    /**
     * {@code POST  /warranty-details} : Create a new warrantyDetails.
     *
     * @param warrantyDetailsDTO the warrantyDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyDetailsDTO, or with status {@code 400 (Bad Request)} if the warrantyDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/warranty-details")
    public ResponseEntity<WarrantyDetailsDTO> createWarrantyDetails(@Valid @RequestBody WarrantyDetailsDTO warrantyDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save WarrantyDetails : {}", warrantyDetailsDTO);
        if (warrantyDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new warrantyDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarrantyDetailsDTO result = warrantyDetailsService.save(warrantyDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/warranty-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /warranty-details/:id} : Updates an existing warrantyDetails.
     *
     * @param id the id of the warrantyDetailsDTO to save.
     * @param warrantyDetailsDTO the warrantyDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warrantyDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/warranty-details/{id}")
    public ResponseEntity<WarrantyDetailsDTO> updateWarrantyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WarrantyDetailsDTO warrantyDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WarrantyDetails : {}, {}", id, warrantyDetailsDTO);
        if (warrantyDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WarrantyDetailsDTO result = warrantyDetailsService.update(warrantyDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /warranty-details/:id} : Partial updates given fields of an existing warrantyDetails, field will ignore if it is null
     *
     * @param id the id of the warrantyDetailsDTO to save.
     * @param warrantyDetailsDTO the warrantyDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the warrantyDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the warrantyDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/warranty-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WarrantyDetailsDTO> partialUpdateWarrantyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WarrantyDetailsDTO warrantyDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WarrantyDetails partially : {}, {}", id, warrantyDetailsDTO);
        if (warrantyDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warrantyDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warrantyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WarrantyDetailsDTO> result = warrantyDetailsService.partialUpdate(warrantyDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /warranty-details} : get all the warrantyDetails.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warrantyDetails in body.
     */
    @GetMapping("/warranty-details")
    public ResponseEntity<List<WarrantyDetailsDTO>> getAllWarrantyDetails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("product-is-null".equals(filter)) {
            log.debug("REST request to get all WarrantyDetailss where product is null");
            return new ResponseEntity<>(warrantyDetailsService.findAllWhereProductIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of WarrantyDetails");
        Page<WarrantyDetailsDTO> page = warrantyDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /warranty-details/:id} : get the "id" warrantyDetails.
     *
     * @param id the id of the warrantyDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warrantyDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/warranty-details/{id}")
    public ResponseEntity<WarrantyDetailsDTO> getWarrantyDetails(@PathVariable Long id) {
        log.debug("REST request to get WarrantyDetails : {}", id);
        Optional<WarrantyDetailsDTO> warrantyDetailsDTO = warrantyDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyDetailsDTO);
    }

    /**
     * {@code DELETE  /warranty-details/:id} : delete the "id" warrantyDetails.
     *
     * @param id the id of the warrantyDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/warranty-details/{id}")
    public ResponseEntity<Void> deleteWarrantyDetails(@PathVariable Long id) {
        log.debug("REST request to delete WarrantyDetails : {}", id);
        warrantyDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
