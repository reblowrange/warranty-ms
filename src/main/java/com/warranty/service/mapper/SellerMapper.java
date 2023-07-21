package com.warranty.service.mapper;

import com.warranty.domain.Seller;
import com.warranty.service.dto.SellerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Seller} and its DTO {@link SellerDTO}.
 */
@Mapper(componentModel = "spring")
public interface SellerMapper extends EntityMapper<SellerDTO, Seller> {}
