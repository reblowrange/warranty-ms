package com.warranty.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.warranty.IntegrationTest;
import com.warranty.domain.WarrantyDetails;
import com.warranty.domain.enumeration.DurationType;
import com.warranty.repository.WarrantyDetailsRepository;
import com.warranty.service.dto.WarrantyDetailsDTO;
import com.warranty.service.mapper.WarrantyDetailsMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link WarrantyDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WarrantyDetailsResourceIT {

    private static final Instant DEFAULT_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BILL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BILL_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final DurationType DEFAULT_DURATION_TYPE = DurationType.DAYS;
    private static final DurationType UPDATED_DURATION_TYPE = DurationType.MONTHS;

    private static final String DEFAULT_SERVICE_CONTACT = "8521310348";
    private static final String UPDATED_SERVICE_CONTACT = "6367671977";

    private static final Boolean DEFAULT_IS_REPLACEABLE = false;
    private static final Boolean UPDATED_IS_REPLACEABLE = true;

    private static final Instant DEFAULT_EXPIRED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_BILL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BILL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BILL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BILL_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/warranty-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WarrantyDetailsRepository warrantyDetailsRepository;

    @Autowired
    private WarrantyDetailsMapper warrantyDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarrantyDetailsMockMvc;

    private WarrantyDetails warrantyDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyDetails createEntity(EntityManager em) {
        WarrantyDetails warrantyDetails = new WarrantyDetails()
            .billDate(DEFAULT_BILL_DATE)
            .billNumber(DEFAULT_BILL_NUMBER)
            .duration(DEFAULT_DURATION)
            .durationType(DEFAULT_DURATION_TYPE)
            .serviceContact(DEFAULT_SERVICE_CONTACT)
            .isReplaceable(DEFAULT_IS_REPLACEABLE)
            .expiredOn(DEFAULT_EXPIRED_ON)
            .bill(DEFAULT_BILL)
            .billContentType(DEFAULT_BILL_CONTENT_TYPE);
        return warrantyDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyDetails createUpdatedEntity(EntityManager em) {
        WarrantyDetails warrantyDetails = new WarrantyDetails()
            .billDate(UPDATED_BILL_DATE)
            .billNumber(UPDATED_BILL_NUMBER)
            .duration(UPDATED_DURATION)
            .durationType(UPDATED_DURATION_TYPE)
            .serviceContact(UPDATED_SERVICE_CONTACT)
            .isReplaceable(UPDATED_IS_REPLACEABLE)
            .expiredOn(UPDATED_EXPIRED_ON)
            .bill(UPDATED_BILL)
            .billContentType(UPDATED_BILL_CONTENT_TYPE);
        return warrantyDetails;
    }

    @BeforeEach
    public void initTest() {
        warrantyDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createWarrantyDetails() throws Exception {
        int databaseSizeBeforeCreate = warrantyDetailsRepository.findAll().size();
        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);
        restWarrantyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        WarrantyDetails testWarrantyDetails = warrantyDetailsList.get(warrantyDetailsList.size() - 1);
        assertThat(testWarrantyDetails.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testWarrantyDetails.getBillNumber()).isEqualTo(DEFAULT_BILL_NUMBER);
        assertThat(testWarrantyDetails.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testWarrantyDetails.getDurationType()).isEqualTo(DEFAULT_DURATION_TYPE);
        assertThat(testWarrantyDetails.getServiceContact()).isEqualTo(DEFAULT_SERVICE_CONTACT);
        assertThat(testWarrantyDetails.getIsReplaceable()).isEqualTo(DEFAULT_IS_REPLACEABLE);
        assertThat(testWarrantyDetails.getExpiredOn()).isEqualTo(DEFAULT_EXPIRED_ON);
        assertThat(testWarrantyDetails.getBill()).isEqualTo(DEFAULT_BILL);
        assertThat(testWarrantyDetails.getBillContentType()).isEqualTo(DEFAULT_BILL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createWarrantyDetailsWithExistingId() throws Exception {
        // Create the WarrantyDetails with an existing ID
        warrantyDetails.setId(1L);
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        int databaseSizeBeforeCreate = warrantyDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBillDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyDetailsRepository.findAll().size();
        // set the field null
        warrantyDetails.setBillDate(null);

        // Create the WarrantyDetails, which fails.
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        restWarrantyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyDetailsRepository.findAll().size();
        // set the field null
        warrantyDetails.setDuration(null);

        // Create the WarrantyDetails, which fails.
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        restWarrantyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyDetailsRepository.findAll().size();
        // set the field null
        warrantyDetails.setDurationType(null);

        // Create the WarrantyDetails, which fails.
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        restWarrantyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpiredOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyDetailsRepository.findAll().size();
        // set the field null
        warrantyDetails.setExpiredOn(null);

        // Create the WarrantyDetails, which fails.
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        restWarrantyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarrantyDetails() throws Exception {
        // Initialize the database
        warrantyDetailsRepository.saveAndFlush(warrantyDetails);

        // Get all the warrantyDetailsList
        restWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].billNumber").value(hasItem(DEFAULT_BILL_NUMBER)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].durationType").value(hasItem(DEFAULT_DURATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceContact").value(hasItem(DEFAULT_SERVICE_CONTACT)))
            .andExpect(jsonPath("$.[*].isReplaceable").value(hasItem(DEFAULT_IS_REPLACEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].expiredOn").value(hasItem(DEFAULT_EXPIRED_ON.toString())))
            .andExpect(jsonPath("$.[*].billContentType").value(hasItem(DEFAULT_BILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bill").value(hasItem(Base64Utils.encodeToString(DEFAULT_BILL))));
    }

    @Test
    @Transactional
    void getWarrantyDetails() throws Exception {
        // Initialize the database
        warrantyDetailsRepository.saveAndFlush(warrantyDetails);

        // Get the warrantyDetails
        restWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, warrantyDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warrantyDetails.getId().intValue()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.billNumber").value(DEFAULT_BILL_NUMBER))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.durationType").value(DEFAULT_DURATION_TYPE.toString()))
            .andExpect(jsonPath("$.serviceContact").value(DEFAULT_SERVICE_CONTACT))
            .andExpect(jsonPath("$.isReplaceable").value(DEFAULT_IS_REPLACEABLE.booleanValue()))
            .andExpect(jsonPath("$.expiredOn").value(DEFAULT_EXPIRED_ON.toString()))
            .andExpect(jsonPath("$.billContentType").value(DEFAULT_BILL_CONTENT_TYPE))
            .andExpect(jsonPath("$.bill").value(Base64Utils.encodeToString(DEFAULT_BILL)));
    }

    @Test
    @Transactional
    void getNonExistingWarrantyDetails() throws Exception {
        // Get the warrantyDetails
        restWarrantyDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWarrantyDetails() throws Exception {
        // Initialize the database
        warrantyDetailsRepository.saveAndFlush(warrantyDetails);

        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();

        // Update the warrantyDetails
        WarrantyDetails updatedWarrantyDetails = warrantyDetailsRepository.findById(warrantyDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWarrantyDetails are not directly saved in db
        em.detach(updatedWarrantyDetails);
        updatedWarrantyDetails
            .billDate(UPDATED_BILL_DATE)
            .billNumber(UPDATED_BILL_NUMBER)
            .duration(UPDATED_DURATION)
            .durationType(UPDATED_DURATION_TYPE)
            .serviceContact(UPDATED_SERVICE_CONTACT)
            .isReplaceable(UPDATED_IS_REPLACEABLE)
            .expiredOn(UPDATED_EXPIRED_ON)
            .bill(UPDATED_BILL)
            .billContentType(UPDATED_BILL_CONTENT_TYPE);
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(updatedWarrantyDetails);

        restWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
        WarrantyDetails testWarrantyDetails = warrantyDetailsList.get(warrantyDetailsList.size() - 1);
        assertThat(testWarrantyDetails.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testWarrantyDetails.getBillNumber()).isEqualTo(UPDATED_BILL_NUMBER);
        assertThat(testWarrantyDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testWarrantyDetails.getDurationType()).isEqualTo(UPDATED_DURATION_TYPE);
        assertThat(testWarrantyDetails.getServiceContact()).isEqualTo(UPDATED_SERVICE_CONTACT);
        assertThat(testWarrantyDetails.getIsReplaceable()).isEqualTo(UPDATED_IS_REPLACEABLE);
        assertThat(testWarrantyDetails.getExpiredOn()).isEqualTo(UPDATED_EXPIRED_ON);
        assertThat(testWarrantyDetails.getBill()).isEqualTo(UPDATED_BILL);
        assertThat(testWarrantyDetails.getBillContentType()).isEqualTo(UPDATED_BILL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingWarrantyDetails() throws Exception {
        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();
        warrantyDetails.setId(count.incrementAndGet());

        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarrantyDetails() throws Exception {
        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();
        warrantyDetails.setId(count.incrementAndGet());

        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarrantyDetails() throws Exception {
        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();
        warrantyDetails.setId(count.incrementAndGet());

        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarrantyDetailsWithPatch() throws Exception {
        // Initialize the database
        warrantyDetailsRepository.saveAndFlush(warrantyDetails);

        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();

        // Update the warrantyDetails using partial update
        WarrantyDetails partialUpdatedWarrantyDetails = new WarrantyDetails();
        partialUpdatedWarrantyDetails.setId(warrantyDetails.getId());

        partialUpdatedWarrantyDetails
            .billNumber(UPDATED_BILL_NUMBER)
            .duration(UPDATED_DURATION)
            .isReplaceable(UPDATED_IS_REPLACEABLE)
            .bill(UPDATED_BILL)
            .billContentType(UPDATED_BILL_CONTENT_TYPE);

        restWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
        WarrantyDetails testWarrantyDetails = warrantyDetailsList.get(warrantyDetailsList.size() - 1);
        assertThat(testWarrantyDetails.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testWarrantyDetails.getBillNumber()).isEqualTo(UPDATED_BILL_NUMBER);
        assertThat(testWarrantyDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testWarrantyDetails.getDurationType()).isEqualTo(DEFAULT_DURATION_TYPE);
        assertThat(testWarrantyDetails.getServiceContact()).isEqualTo(DEFAULT_SERVICE_CONTACT);
        assertThat(testWarrantyDetails.getIsReplaceable()).isEqualTo(UPDATED_IS_REPLACEABLE);
        assertThat(testWarrantyDetails.getExpiredOn()).isEqualTo(DEFAULT_EXPIRED_ON);
        assertThat(testWarrantyDetails.getBill()).isEqualTo(UPDATED_BILL);
        assertThat(testWarrantyDetails.getBillContentType()).isEqualTo(UPDATED_BILL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateWarrantyDetailsWithPatch() throws Exception {
        // Initialize the database
        warrantyDetailsRepository.saveAndFlush(warrantyDetails);

        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();

        // Update the warrantyDetails using partial update
        WarrantyDetails partialUpdatedWarrantyDetails = new WarrantyDetails();
        partialUpdatedWarrantyDetails.setId(warrantyDetails.getId());

        partialUpdatedWarrantyDetails
            .billDate(UPDATED_BILL_DATE)
            .billNumber(UPDATED_BILL_NUMBER)
            .duration(UPDATED_DURATION)
            .durationType(UPDATED_DURATION_TYPE)
            .serviceContact(UPDATED_SERVICE_CONTACT)
            .isReplaceable(UPDATED_IS_REPLACEABLE)
            .expiredOn(UPDATED_EXPIRED_ON)
            .bill(UPDATED_BILL)
            .billContentType(UPDATED_BILL_CONTENT_TYPE);

        restWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
        WarrantyDetails testWarrantyDetails = warrantyDetailsList.get(warrantyDetailsList.size() - 1);
        assertThat(testWarrantyDetails.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testWarrantyDetails.getBillNumber()).isEqualTo(UPDATED_BILL_NUMBER);
        assertThat(testWarrantyDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testWarrantyDetails.getDurationType()).isEqualTo(UPDATED_DURATION_TYPE);
        assertThat(testWarrantyDetails.getServiceContact()).isEqualTo(UPDATED_SERVICE_CONTACT);
        assertThat(testWarrantyDetails.getIsReplaceable()).isEqualTo(UPDATED_IS_REPLACEABLE);
        assertThat(testWarrantyDetails.getExpiredOn()).isEqualTo(UPDATED_EXPIRED_ON);
        assertThat(testWarrantyDetails.getBill()).isEqualTo(UPDATED_BILL);
        assertThat(testWarrantyDetails.getBillContentType()).isEqualTo(UPDATED_BILL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingWarrantyDetails() throws Exception {
        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();
        warrantyDetails.setId(count.incrementAndGet());

        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warrantyDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarrantyDetails() throws Exception {
        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();
        warrantyDetails.setId(count.incrementAndGet());

        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarrantyDetails() throws Exception {
        int databaseSizeBeforeUpdate = warrantyDetailsRepository.findAll().size();
        warrantyDetails.setId(count.incrementAndGet());

        // Create the WarrantyDetails
        WarrantyDetailsDTO warrantyDetailsDTO = warrantyDetailsMapper.toDto(warrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warrantyDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyDetails in the database
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarrantyDetails() throws Exception {
        // Initialize the database
        warrantyDetailsRepository.saveAndFlush(warrantyDetails);

        int databaseSizeBeforeDelete = warrantyDetailsRepository.findAll().size();

        // Delete the warrantyDetails
        restWarrantyDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, warrantyDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WarrantyDetails> warrantyDetailsList = warrantyDetailsRepository.findAll();
        assertThat(warrantyDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
