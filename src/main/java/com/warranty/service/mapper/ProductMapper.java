package com.warranty.service.mapper;

import com.warranty.domain.Product;
import com.warranty.domain.Seller;
import com.warranty.domain.WarrantyDetails;
import com.warranty.service.dto.ProductDTO;
import com.warranty.service.dto.SellerDTO;
import com.warranty.service.dto.WarrantyDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "seller", source = "seller", qualifiedByName = "sellerId")
    @Mapping(target = "warrantyDetails", source = "warrantyDetails", qualifiedByName = "warrantyDetailsId")
    ProductDTO toDto(Product s);

    @Named("sellerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SellerDTO toDtoSellerId(Seller seller);

    @Named("warrantyDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarrantyDetailsDTO toDtoWarrantyDetailsId(WarrantyDetails warrantyDetails);
}
