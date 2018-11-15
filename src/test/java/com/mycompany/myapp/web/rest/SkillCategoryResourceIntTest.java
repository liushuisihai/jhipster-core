package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.SkillCategory;
import com.mycompany.myapp.repository.SkillCategoryRepository;
import com.mycompany.myapp.service.SkillCategoryService;
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
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SkillCategoryResource REST controller.
 *
 * @see SkillCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class SkillCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PID = "AAAAAAAAAA";
    private static final String UPDATED_PID = "BBBBBBBBBB";

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @Autowired
    private SkillCategoryService skillCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillCategoryMockMvc;

    private SkillCategory skillCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillCategoryResource skillCategoryResource = new SkillCategoryResource(skillCategoryService);
        this.restSkillCategoryMockMvc = MockMvcBuilders.standaloneSetup(skillCategoryResource)
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
    public static SkillCategory createEntity(EntityManager em) {
        SkillCategory skillCategory = new SkillCategory()
            .name(DEFAULT_NAME)
            .pid(DEFAULT_PID);
        return skillCategory;
    }

    @Before
    public void initTest() {
        skillCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillCategory() throws Exception {
        int databaseSizeBeforeCreate = skillCategoryRepository.findAll().size();

        // Create the SkillCategory
        restSkillCategoryMockMvc.perform(post("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isCreated());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillCategory.getPid()).isEqualTo(DEFAULT_PID);
    }

    @Test
    @Transactional
    public void createSkillCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillCategoryRepository.findAll().size();

        // Create the SkillCategory with an existing ID
        skillCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillCategoryMockMvc.perform(post("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillCategoryRepository.findAll().size();
        // set the field null
        skillCategory.setName(null);

        // Create the SkillCategory, which fails.

        restSkillCategoryMockMvc.perform(post("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPidIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillCategoryRepository.findAll().size();
        // set the field null
        skillCategory.setPid(null);

        // Create the SkillCategory, which fails.

        restSkillCategoryMockMvc.perform(post("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkillCategories() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList
        restSkillCategoryMockMvc.perform(get("/api/skill-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID.toString())));
    }
    
    @Test
    @Transactional
    public void getSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get the skillCategory
        restSkillCategoryMockMvc.perform(get("/api/skill-categories/{id}", skillCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkillCategory() throws Exception {
        // Get the skillCategory
        restSkillCategoryMockMvc.perform(get("/api/skill-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryService.save(skillCategory);

        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Update the skillCategory
        SkillCategory updatedSkillCategory = skillCategoryRepository.findById(skillCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSkillCategory are not directly saved in db
        em.detach(updatedSkillCategory);
        updatedSkillCategory
            .name(UPDATED_NAME)
            .pid(UPDATED_PID);

        restSkillCategoryMockMvc.perform(put("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillCategory)))
            .andExpect(status().isOk());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillCategory.getPid()).isEqualTo(UPDATED_PID);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Create the SkillCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc.perform(put("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryService.save(skillCategory);

        int databaseSizeBeforeDelete = skillCategoryRepository.findAll().size();

        // Get the skillCategory
        restSkillCategoryMockMvc.perform(delete("/api/skill-categories/{id}", skillCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillCategory.class);
        SkillCategory skillCategory1 = new SkillCategory();
        skillCategory1.setId(1L);
        SkillCategory skillCategory2 = new SkillCategory();
        skillCategory2.setId(skillCategory1.getId());
        assertThat(skillCategory1).isEqualTo(skillCategory2);
        skillCategory2.setId(2L);
        assertThat(skillCategory1).isNotEqualTo(skillCategory2);
        skillCategory1.setId(null);
        assertThat(skillCategory1).isNotEqualTo(skillCategory2);
    }
}
