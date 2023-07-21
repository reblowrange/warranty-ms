package com.warranty.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.warranty.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarrantyDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyDetailsDTO.class);
        WarrantyDetailsDTO warrantyDetailsDTO1 = new WarrantyDetailsDTO();
        warrantyDetailsDTO1.setId(1L);
        WarrantyDetailsDTO warrantyDetailsDTO2 = new WarrantyDetailsDTO();
        assertThat(warrantyDetailsDTO1).isNotEqualTo(warrantyDetailsDTO2);
        warrantyDetailsDTO2.setId(warrantyDetailsDTO1.getId());
        assertThat(warrantyDetailsDTO1).isEqualTo(warrantyDetailsDTO2);
        warrantyDetailsDTO2.setId(2L);
        assertThat(warrantyDetailsDTO1).isNotEqualTo(warrantyDetailsDTO2);
        warrantyDetailsDTO1.setId(null);
        assertThat(warrantyDetailsDTO1).isNotEqualTo(warrantyDetailsDTO2);
    }
}
