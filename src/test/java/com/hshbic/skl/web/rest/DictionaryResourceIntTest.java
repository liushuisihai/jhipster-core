package com.hshbic.skl.web.rest;

import com.hshbic.skl.MyappApp;

import com.hshbic.skl.domain.Dictionary;
import com.hshbic.skl.repository.DictionaryRepository;
import com.hshbic.skl.service.DictionaryService;
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
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.hshbic.skl.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hshbic.skl.domain.enumeration.DictionaryType;
/**
 * Test class for the DictionaryResource REST controller.
 *
 * @see DictionaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class DictionaryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DictionaryType DEFAULT_TYPE = DictionaryType.SYS;
    private static final DictionaryType UPDATED_TYPE = DictionaryType.CUSTOM;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDictionaryMockMvc;

    private Dictionary dictionary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DictionaryResource dictionaryResource = new DictionaryResource(dictionaryService);
        this.restDictionaryMockMvc = MockMvcBuilders.standaloneSetup(dictionaryResource)
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
    public static Dictionary createEntity(EntityManager em) {
        Dictionary dictionary = new Dictionary()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .createTime(DEFAULT_CREATE_TIME);
        return dictionary;
    }

    @Before
    public void initTest() {
        dictionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictionary() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();

        // Create the Dictionary
        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isCreated());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDictionary.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDictionary.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createDictionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();

        // Create the Dictionary with an existing ID
        dictionary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setName(null);

        // Create the Dictionary, which fails.

        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setCreateTime(null);

        // Create the Dictionary, which fails.

        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDictionaries() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get all the dictionaryList
        restDictionaryMockMvc.perform(get("/api/dictionaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get the dictionary
        restDictionaryMockMvc.perform(get("/api/dictionaries/{id}", dictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dictionary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDictionary() throws Exception {
        // Get the dictionary
        restDictionaryMockMvc.perform(get("/api/dictionaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictionary() throws Exception {
        // Initialize the database
        dictionaryService.save(dictionary);

        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Update the dictionary
        Dictionary updatedDictionary = dictionaryRepository.findById(dictionary.getId()).get();
        // Disconnect from session so that the updates on updatedDictionary are not directly saved in db
        em.detach(updatedDictionary);
        updatedDictionary
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .createTime(UPDATED_CREATE_TIME);

        restDictionaryMockMvc.perform(put("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDictionary)))
            .andExpect(status().isOk());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDictionary.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDictionary.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Create the Dictionary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictionaryMockMvc.perform(put("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDictionary() throws Exception {
        // Initialize the database
        dictionaryService.save(dictionary);

        int databaseSizeBeforeDelete = dictionaryRepository.findAll().size();

        // Get the dictionary
        restDictionaryMockMvc.perform(delete("/api/dictionaries/{id}", dictionary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dictionary.class);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setId(1L);
        Dictionary dictionary2 = new Dictionary();
        dictionary2.setId(dictionary1.getId());
        assertThat(dictionary1).isEqualTo(dictionary2);
        dictionary2.setId(2L);
        assertThat(dictionary1).isNotEqualTo(dictionary2);
        dictionary1.setId(null);
        assertThat(dictionary1).isNotEqualTo(dictionary2);
    }
}
