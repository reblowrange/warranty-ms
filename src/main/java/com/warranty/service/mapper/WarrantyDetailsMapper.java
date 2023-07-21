package com.warranty.service.mapper;

import com.warranty.domain.WarrantyDetails;
import com.warranty.service.dto.WarrantyDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WarrantyDetails} and its DTO {@link WarrantyDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface WarrantyDetailsMapper extends EntityMapper<WarrantyDetailsDTO, WarrantyDetails> {}
