package com.warranty.service.impl;

import com.warranty.domain.WarrantyDetails;
import com.warranty.repository.WarrantyDetailsRepository;
import com.warranty.service.WarrantyDetailsService;
import com.warranty.service.dto.WarrantyDetailsDTO;
import com.warranty.service.mapper.WarrantyDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WarrantyDetails}.
 */
@Service
@Transactional
public class WarrantyDetailsServiceImpl implements WarrantyDetailsService {

    private final Logger log = LoggerFactory.getLogger(WarrantyDetailsServiceImpl.class);

    private final WarrantyDetailsRepository warrantyDetailsRepository;

    private final WarrantyDetailsMapper warrantyDetailsMapper;

    public WarrantyDetailsServiceImpl(WarrantyDetailsRepository warrantyDetailsRepository, WarrantyDetailsMapper warrantyDetailsMapper) {
        this.warrantyDetailsRepository = warrantyDetailsRepository;
        this.warrantyDetailsMapper = warrantyDetailsMapper;
    }

    @Override
    public WarrantyDetailsDTO save(WarrantyDetailsDTO warrantyDetailsDTO) {
        log.debug("Request to save WarrantyDetails : {}", warrantyDetailsDTO);
        WarrantyDetails warrantyDetails = warrantyDetailsMapper.toEntity(warrantyDetailsDTO);
        warrantyDetails = warrantyDetailsRepository.save(warrantyDetails);
        return warrantyDetailsMapper.toDto(warrantyDetails);
    }

    @Override
    public WarrantyDetailsDTO update(WarrantyDetailsDTO warrantyDetailsDTO) {
        log.debug("Request to update WarrantyDetails : {}", warrantyDetailsDTO);
        WarrantyDetails warrantyDetails = warrantyDetailsMapper.toEntity(warrantyDetailsDTO);
        warrantyDetails = warrantyDetailsRepository.save(warrantyDetails);
        return warrantyDetailsMapper.toDto(warrantyDetails);
    }

    @Override
    public Optional<WarrantyDetailsDTO> partialUpdate(WarrantyDetailsDTO warrantyDetailsDTO) {
        log.debug("Request to partially update WarrantyDetails : {}", warrantyDetailsDTO);

        return warrantyDetailsRepository
            .findById(warrantyDetailsDTO.getId())
            .map(existingWarrantyDetails -> {
                warrantyDetailsMapper.partialUpdate(existingWarrantyDetails, warrantyDetailsDTO);

                return existingWarrantyDetails;
            })
            .map(warrantyDetailsRepository::save)
            .map(warrantyDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarrantyDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WarrantyDetails");
        return warrantyDetailsRepository.findAll(pageable).map(warrantyDetailsMapper::toDto);
    }

    /**
     *  Get all the warrantyDetails where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WarrantyDetailsDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all warrantyDetails where Product is null");
        return StreamSupport
            .stream(warrantyDetailsRepository.findAll().spliterator(), false)
            .filter(warrantyDetails -> warrantyDetails.getProduct() == null)
            .map(warrantyDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyDetailsDTO> findOne(Long id) {
        log.debug("Request to get WarrantyDetails : {}", id);
        return warrantyDetailsRepository.findById(id).map(warrantyDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WarrantyDetails : {}", id);
        warrantyDetailsRepository.deleteById(id);
    }
}
