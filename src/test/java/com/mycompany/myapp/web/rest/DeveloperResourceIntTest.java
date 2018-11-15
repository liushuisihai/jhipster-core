package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Developer;
import com.mycompany.myapp.repository.DeveloperRepository;
import com.mycompany.myapp.service.DeveloperService;
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
 * Test class for the DeveloperResource REST controller.
 *
 * @see DeveloperResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class DeveloperResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "2";
    private static final String UPDATED_TYPE = "2B";

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeveloperMockMvc;

    private Developer developer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeveloperResource developerResource = new DeveloperResource(developerService);
        this.restDeveloperMockMvc = MockMvcBuilders.standaloneSetup(developerResource)
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
    public static Developer createEntity(EntityManager em) {
        Developer developer = new Developer()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .type(DEFAULT_TYPE)
            .createTime(DEFAULT_CREATE_TIME);
        return developer;
    }

    @Before
    public void initTest() {
        developer = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeveloper() throws Exception {
        int databaseSizeBeforeCreate = developerRepository.findAll().size();

        // Create the Developer
        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isCreated());

        // Validate the Developer in the database
        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeCreate + 1);
        Developer testDeveloper = developerList.get(developerList.size() - 1);
        assertThat(testDeveloper.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDeveloper.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDeveloper.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDeveloper.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeveloper.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createDeveloperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = developerRepository.findAll().size();

        // Create the Developer with an existing ID
        developer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = developerRepository.findAll().size();
        // set the field null
        developer.setUsername(null);

        // Create the Developer, which fails.

        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = developerRepository.findAll().size();
        // set the field null
        developer.setPassword(null);

        // Create the Developer, which fails.

        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = developerRepository.findAll().size();
        // set the field null
        developer.setEmail(null);

        // Create the Developer, which fails.

        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = developerRepository.findAll().size();
        // set the field null
        developer.setType(null);

        // Create the Developer, which fails.

        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = developerRepository.findAll().size();
        // set the field null
        developer.setCreateTime(null);

        // Create the Developer, which fails.

        restDeveloperMockMvc.perform(post("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevelopers() throws Exception {
        // Initialize the database
        developerRepository.saveAndFlush(developer);

        // Get all the developerList
        restDeveloperMockMvc.perform(get("/api/developers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(developer.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getDeveloper() throws Exception {
        // Initialize the database
        developerRepository.saveAndFlush(developer);

        // Get the developer
        restDeveloperMockMvc.perform(get("/api/developers/{id}", developer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(developer.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeveloper() throws Exception {
        // Get the developer
        restDeveloperMockMvc.perform(get("/api/developers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeveloper() throws Exception {
        // Initialize the database
        developerService.save(developer);

        int databaseSizeBeforeUpdate = developerRepository.findAll().size();

        // Update the developer
        Developer updatedDeveloper = developerRepository.findById(developer.getId()).get();
        // Disconnect from session so that the updates on updatedDeveloper are not directly saved in db
        em.detach(updatedDeveloper);
        updatedDeveloper
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .type(UPDATED_TYPE)
            .createTime(UPDATED_CREATE_TIME);

        restDeveloperMockMvc.perform(put("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeveloper)))
            .andExpect(status().isOk());

        // Validate the Developer in the database
        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeUpdate);
        Developer testDeveloper = developerList.get(developerList.size() - 1);
        assertThat(testDeveloper.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDeveloper.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDeveloper.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDeveloper.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeveloper.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeveloper() throws Exception {
        int databaseSizeBeforeUpdate = developerRepository.findAll().size();

        // Create the Developer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeveloperMockMvc.perform(put("/api/developers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(developer)))
            .andExpect(status().isBadRequest());

        // Validate the Developer in the database
        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeveloper() throws Exception {
        // Initialize the database
        developerService.save(developer);

        int databaseSizeBeforeDelete = developerRepository.findAll().size();

        // Get the developer
        restDeveloperMockMvc.perform(delete("/api/developers/{id}", developer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Developer> developerList = developerRepository.findAll();
        assertThat(developerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Developer.class);
        Developer developer1 = new Developer();
        developer1.setId(1L);
        Developer developer2 = new Developer();
        developer2.setId(developer1.getId());
        assertThat(developer1).isEqualTo(developer2);
        developer2.setId(2L);
        assertThat(developer1).isNotEqualTo(developer2);
        developer1.setId(null);
        assertThat(developer1).isNotEqualTo(developer2);
    }
}
