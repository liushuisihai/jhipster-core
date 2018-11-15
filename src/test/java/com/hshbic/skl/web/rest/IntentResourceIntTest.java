package com.hshbic.skl.web.rest;

import com.hshbic.skl.MyappApp;

import com.hshbic.skl.domain.Intent;
import com.hshbic.skl.repository.IntentRepository;
import com.hshbic.skl.service.IntentService;
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
 * Test class for the IntentResource REST controller.
 *
 * @see IntentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class IntentResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private IntentRepository intentRepository;

    @Autowired
    private IntentService intentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntentMockMvc;

    private Intent intent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntentResource intentResource = new IntentResource(intentService);
        this.restIntentMockMvc = MockMvcBuilders.standaloneSetup(intentResource)
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
    public static Intent createEntity(EntityManager em) {
        Intent intent = new Intent()
            .content(DEFAULT_CONTENT)
            .delFlag(DEFAULT_DEL_FLAG)
            .createTime(DEFAULT_CREATE_TIME);
        return intent;
    }

    @Before
    public void initTest() {
        intent = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntent() throws Exception {
        int databaseSizeBeforeCreate = intentRepository.findAll().size();

        // Create the Intent
        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isCreated());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeCreate + 1);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testIntent.isDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testIntent.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createIntentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intentRepository.findAll().size();

        // Create the Intent with an existing ID
        intent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setContent(null);

        // Create the Intent, which fails.

        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setDelFlag(null);

        // Create the Intent, which fails.

        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setCreateTime(null);

        // Create the Intent, which fails.

        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntents() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        // Get all the intentList
        restIntentMockMvc.perform(get("/api/intents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intent.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getIntent() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        // Get the intent
        restIntentMockMvc.perform(get("/api/intents/{id}", intent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intent.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingIntent() throws Exception {
        // Get the intent
        restIntentMockMvc.perform(get("/api/intents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntent() throws Exception {
        // Initialize the database
        intentService.save(intent);

        int databaseSizeBeforeUpdate = intentRepository.findAll().size();

        // Update the intent
        Intent updatedIntent = intentRepository.findById(intent.getId()).get();
        // Disconnect from session so that the updates on updatedIntent are not directly saved in db
        em.detach(updatedIntent);
        updatedIntent
            .content(UPDATED_CONTENT)
            .delFlag(UPDATED_DEL_FLAG)
            .createTime(UPDATED_CREATE_TIME);

        restIntentMockMvc.perform(put("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntent)))
            .andExpect(status().isOk());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testIntent.isDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testIntent.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingIntent() throws Exception {
        int databaseSizeBeforeUpdate = intentRepository.findAll().size();

        // Create the Intent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntentMockMvc.perform(put("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntent() throws Exception {
        // Initialize the database
        intentService.save(intent);

        int databaseSizeBeforeDelete = intentRepository.findAll().size();

        // Get the intent
        restIntentMockMvc.perform(delete("/api/intents/{id}", intent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intent.class);
        Intent intent1 = new Intent();
        intent1.setId(1L);
        Intent intent2 = new Intent();
        intent2.setId(intent1.getId());
        assertThat(intent1).isEqualTo(intent2);
        intent2.setId(2L);
        assertThat(intent1).isNotEqualTo(intent2);
        intent1.setId(null);
        assertThat(intent1).isNotEqualTo(intent2);
    }
}
