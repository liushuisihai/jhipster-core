package com.hshbic.skl.domain.web.rest;

import com.hshbic.skl.domain.MyappApp;

import com.hshbic.skl.domain.domain.WordGroup;
import com.hshbic.skl.domain.repository.WordGroupRepository;
import com.hshbic.skl.domain.service.WordGroupService;
import com.hshbic.skl.domain.web.rest.errors.ExceptionTranslator;

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


import static com.hshbic.skl.domain.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WordGroupResource REST controller.
 *
 * @see WordGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class WordGroupResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private WordGroupRepository wordGroupRepository;

    @Autowired
    private WordGroupService wordGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWordGroupMockMvc;

    private WordGroup wordGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WordGroupResource wordGroupResource = new WordGroupResource(wordGroupService);
        this.restWordGroupMockMvc = MockMvcBuilders.standaloneSetup(wordGroupResource)
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
    public static WordGroup createEntity(EntityManager em) {
        WordGroup wordGroup = new WordGroup()
            .content(DEFAULT_CONTENT);
        return wordGroup;
    }

    @Before
    public void initTest() {
        wordGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createWordGroup() throws Exception {
        int databaseSizeBeforeCreate = wordGroupRepository.findAll().size();

        // Create the WordGroup
        restWordGroupMockMvc.perform(post("/api/word-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordGroup)))
            .andExpect(status().isCreated());

        // Validate the WordGroup in the database
        List<WordGroup> wordGroupList = wordGroupRepository.findAll();
        assertThat(wordGroupList).hasSize(databaseSizeBeforeCreate + 1);
        WordGroup testWordGroup = wordGroupList.get(wordGroupList.size() - 1);
        assertThat(testWordGroup.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createWordGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordGroupRepository.findAll().size();

        // Create the WordGroup with an existing ID
        wordGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordGroupMockMvc.perform(post("/api/word-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordGroup)))
            .andExpect(status().isBadRequest());

        // Validate the WordGroup in the database
        List<WordGroup> wordGroupList = wordGroupRepository.findAll();
        assertThat(wordGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = wordGroupRepository.findAll().size();
        // set the field null
        wordGroup.setContent(null);

        // Create the WordGroup, which fails.

        restWordGroupMockMvc.perform(post("/api/word-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordGroup)))
            .andExpect(status().isBadRequest());

        List<WordGroup> wordGroupList = wordGroupRepository.findAll();
        assertThat(wordGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWordGroups() throws Exception {
        // Initialize the database
        wordGroupRepository.saveAndFlush(wordGroup);

        // Get all the wordGroupList
        restWordGroupMockMvc.perform(get("/api/word-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wordGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }
    
    @Test
    @Transactional
    public void getWordGroup() throws Exception {
        // Initialize the database
        wordGroupRepository.saveAndFlush(wordGroup);

        // Get the wordGroup
        restWordGroupMockMvc.perform(get("/api/word-groups/{id}", wordGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wordGroup.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWordGroup() throws Exception {
        // Get the wordGroup
        restWordGroupMockMvc.perform(get("/api/word-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWordGroup() throws Exception {
        // Initialize the database
        wordGroupService.save(wordGroup);

        int databaseSizeBeforeUpdate = wordGroupRepository.findAll().size();

        // Update the wordGroup
        WordGroup updatedWordGroup = wordGroupRepository.findById(wordGroup.getId()).get();
        // Disconnect from session so that the updates on updatedWordGroup are not directly saved in db
        em.detach(updatedWordGroup);
        updatedWordGroup
            .content(UPDATED_CONTENT);

        restWordGroupMockMvc.perform(put("/api/word-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWordGroup)))
            .andExpect(status().isOk());

        // Validate the WordGroup in the database
        List<WordGroup> wordGroupList = wordGroupRepository.findAll();
        assertThat(wordGroupList).hasSize(databaseSizeBeforeUpdate);
        WordGroup testWordGroup = wordGroupList.get(wordGroupList.size() - 1);
        assertThat(testWordGroup.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingWordGroup() throws Exception {
        int databaseSizeBeforeUpdate = wordGroupRepository.findAll().size();

        // Create the WordGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordGroupMockMvc.perform(put("/api/word-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wordGroup)))
            .andExpect(status().isBadRequest());

        // Validate the WordGroup in the database
        List<WordGroup> wordGroupList = wordGroupRepository.findAll();
        assertThat(wordGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWordGroup() throws Exception {
        // Initialize the database
        wordGroupService.save(wordGroup);

        int databaseSizeBeforeDelete = wordGroupRepository.findAll().size();

        // Get the wordGroup
        restWordGroupMockMvc.perform(delete("/api/word-groups/{id}", wordGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WordGroup> wordGroupList = wordGroupRepository.findAll();
        assertThat(wordGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordGroup.class);
        WordGroup wordGroup1 = new WordGroup();
        wordGroup1.setId(1L);
        WordGroup wordGroup2 = new WordGroup();
        wordGroup2.setId(wordGroup1.getId());
        assertThat(wordGroup1).isEqualTo(wordGroup2);
        wordGroup2.setId(2L);
        assertThat(wordGroup1).isNotEqualTo(wordGroup2);
        wordGroup1.setId(null);
        assertThat(wordGroup1).isNotEqualTo(wordGroup2);
    }
}
