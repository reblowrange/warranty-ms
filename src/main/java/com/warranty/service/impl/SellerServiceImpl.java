package com.warranty.service.impl;

import com.warranty.domain.Seller;
import com.warranty.repository.SellerRepository;
import com.warranty.service.SellerService;
import com.warranty.service.dto.SellerDTO;
import com.warranty.service.mapper.SellerMapper;
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
 * Service Implementation for managing {@link Seller}.
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    private final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
    }

    @Override
    public SellerDTO save(SellerDTO sellerDTO) {
        log.debug("Request to save Seller : {}", sellerDTO);
        Seller seller = sellerMapper.toEntity(sellerDTO);
        seller = sellerRepository.save(seller);
        return sellerMapper.toDto(seller);
    }

    @Override
    public SellerDTO update(SellerDTO sellerDTO) {
        log.debug("Request to update Seller : {}", sellerDTO);
        Seller seller = sellerMapper.toEntity(sellerDTO);
        seller = sellerRepository.save(seller);
        return sellerMapper.toDto(seller);
    }

    @Override
    public Optional<SellerDTO> partialUpdate(SellerDTO sellerDTO) {
        log.debug("Request to partially update Seller : {}", sellerDTO);

        return sellerRepository
            .findById(sellerDTO.getId())
            .map(existingSeller -> {
                sellerMapper.partialUpdate(existingSeller, sellerDTO);

                return existingSeller;
            })
            .map(sellerRepository::save)
            .map(sellerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SellerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sellers");
        return sellerRepository.findAll(pageable).map(sellerMapper::toDto);
    }

    /**
     *  Get all the sellers where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SellerDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all sellers where Product is null");
        return StreamSupport
            .stream(sellerRepository.findAll().spliterator(), false)
            .filter(seller -> seller.getProduct() == null)
            .map(sellerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SellerDTO> findOne(Long id) {
        log.debug("Request to get Seller : {}", id);
        return sellerRepository.findById(id).map(sellerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seller : {}", id);
        sellerRepository.deleteById(id);
    }
}
