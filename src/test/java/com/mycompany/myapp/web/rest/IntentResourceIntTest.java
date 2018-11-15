package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Intent;
import com.mycompany.myapp.repository.IntentRepository;
import com.mycompany.myapp.service.IntentService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
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

    private static final Long DEFAULT_INTENT_ID = 1L;
    private static final Long UPDATED_INTENT_ID = 2L;

    private static final Long DEFAULT_SKILL_ID = 1L;
    private static final Long UPDATED_SKILL_ID = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "2";
    private static final String UPDATED_STATUS = "1";

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());

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
            .intentId(DEFAULT_INTENT_ID)
            .skillId(DEFAULT_SKILL_ID)
            .content(DEFAULT_CONTENT)
            .status(DEFAULT_STATUS)
            .taskId(DEFAULT_TASK_ID)
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
        assertThat(testIntent.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testIntent.getSkillId()).isEqualTo(DEFAULT_SKILL_ID);
        assertThat(testIntent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testIntent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIntent.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
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
    public void checkIntentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setIntentId(null);

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
    public void checkSkillIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setSkillId(null);

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
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setStatus(null);

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
    public void checkTaskIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setTaskId(null);

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
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(hasItem(DEFAULT_SKILL_ID.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
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
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID.intValue()))
            .andExpect(jsonPath("$.skillId").value(DEFAULT_SKILL_ID.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
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
            .intentId(UPDATED_INTENT_ID)
            .skillId(UPDATED_SKILL_ID)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .taskId(UPDATED_TASK_ID)
            .createTime(UPDATED_CREATE_TIME);

        restIntentMockMvc.perform(put("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntent)))
            .andExpect(status().isOk());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testIntent.getSkillId()).isEqualTo(UPDATED_SKILL_ID);
        assertThat(testIntent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testIntent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIntent.getTaskId()).isEqualTo(UPDATED_TASK_ID);
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
