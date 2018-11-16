package com.hshbic.skl.web.rest;

import com.hshbic.skl.MyappApp;

import com.hshbic.skl.domain.Slots;
import com.hshbic.skl.repository.SlotsRepository;
import com.hshbic.skl.service.SlotsService;
import com.hshbic.skl.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.hshbic.skl.web.rest.TestUtil.sameInstant;
import static com.hshbic.skl.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SlotsResource REST controller.
 *
 * @see SlotsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class SlotsResourceIntTest {

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SlotsRepository slotsRepository;

    @Autowired
    private SlotsService slotsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSlotsMockMvc;

    private Slots slots;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SlotsResource slotsResource = new SlotsResource(slotsService);
        this.restSlotsMockMvc = MockMvcBuilders.standaloneSetup(slotsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slots createEntity(EntityManager em) {
        Slots slots = new Slots()
            .sortNum(DEFAULT_SORT_NUM)
            .isRequired(DEFAULT_IS_REQUIRED)
            .question(DEFAULT_QUESTION)
            .delFlag(DEFAULT_DEL_FLAG)
            .createTime(DEFAULT_CREATE_TIME);
        return slots;
    }

    @Before
    public void initTest() {
        slots = createEntity(em);
    }

    @Test
    @Transactional
    public void createSlots() throws Exception {
        int databaseSizeBeforeCreate = slotsRepository.findAll().size();

        // Create the Slots
        restSlotsMockMvc.perform(post("/api/slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slots)))
            .andExpect(status().isCreated());

        // Validate the Slots in the database
        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeCreate + 1);
        Slots testSlots = slotsList.get(slotsList.size() - 1);
        assertThat(testSlots.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
        assertThat(testSlots.isIsRequired()).isEqualTo(DEFAULT_IS_REQUIRED);
        assertThat(testSlots.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testSlots.isDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testSlots.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createSlotsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = slotsRepository.findAll().size();

        // Create the Slots with an existing ID
        slots.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlotsMockMvc.perform(post("/api/slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slots)))
            .andExpect(status().isBadRequest());

        // Validate the Slots in the database
        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = slotsRepository.findAll().size();
        // set the field null
        slots.setSortNum(null);

        // Create the Slots, which fails.

        restSlotsMockMvc.perform(post("/api/slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slots)))
            .andExpect(status().isBadRequest());

        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = slotsRepository.findAll().size();
        // set the field null
        slots.setCreateTime(null);

        // Create the Slots, which fails.

        restSlotsMockMvc.perform(post("/api/slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slots)))
            .andExpect(status().isBadRequest());

        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSlots() throws Exception {
        // Initialize the database
        slotsRepository.saveAndFlush(slots);

        // Get all the slotsList
        restSlotsMockMvc.perform(get("/api/slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slots.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getSlots() throws Exception {
        // Initialize the database
        slotsRepository.saveAndFlush(slots);

        // Get the slots
        restSlotsMockMvc.perform(get("/api/slots/{id}", slots.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(slots.getId().intValue()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSlots() throws Exception {
        // Get the slots
        restSlotsMockMvc.perform(get("/api/slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSlots() throws Exception {
        // Initialize the database
        slotsService.save(slots);

        int databaseSizeBeforeUpdate = slotsRepository.findAll().size();

        // Update the slots
        Slots updatedSlots = slotsRepository.findById(slots.getId()).get();
        // Disconnect from session so that the updates on updatedSlots are not directly saved in db
        em.detach(updatedSlots);
        updatedSlots
            .sortNum(UPDATED_SORT_NUM)
            .isRequired(UPDATED_IS_REQUIRED)
            .question(UPDATED_QUESTION)
            .delFlag(UPDATED_DEL_FLAG)
            .createTime(UPDATED_CREATE_TIME);

        restSlotsMockMvc.perform(put("/api/slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSlots)))
            .andExpect(status().isOk());

        // Validate the Slots in the database
        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeUpdate);
        Slots testSlots = slotsList.get(slotsList.size() - 1);
        assertThat(testSlots.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
        assertThat(testSlots.isIsRequired()).isEqualTo(UPDATED_IS_REQUIRED);
        assertThat(testSlots.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testSlots.isDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testSlots.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSlots() throws Exception {
        int databaseSizeBeforeUpdate = slotsRepository.findAll().size();

        // Create the Slots

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlotsMockMvc.perform(put("/api/slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slots)))
            .andExpect(status().isBadRequest());

        // Validate the Slots in the database
        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSlots() throws Exception {
        // Initialize the database
        slotsService.save(slots);

        int databaseSizeBeforeDelete = slotsRepository.findAll().size();

        // Get the slots
        restSlotsMockMvc.perform(delete("/api/slots/{id}", slots.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Slots> slotsList = slotsRepository.findAll();
        assertThat(slotsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Slots.class);
        Slots slots1 = new Slots();
        slots1.setId(1L);
        Slots slots2 = new Slots();
        slots2.setId(slots1.getId());
        assertThat(slots1).isEqualTo(slots2);
        slots2.setId(2L);
        assertThat(slots1).isNotEqualTo(slots2);
        slots1.setId(null);
        assertThat(slots1).isNotEqualTo(slots2);
    }
}
