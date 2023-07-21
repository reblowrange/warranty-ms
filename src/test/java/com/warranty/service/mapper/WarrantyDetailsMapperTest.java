package com.warranty.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarrantyDetailsMapperTest {

    private WarrantyDetailsMapper warrantyDetailsMapper;

    @BeforeEach
    public void setUp() {
        warrantyDetailsMapper = new WarrantyDetailsMapperImpl();
    }
}
