package com.hshbic.skl.web.rest;

import com.hshbic.skl.MyappApp;

import com.hshbic.skl.domain.UserStmt;
import com.hshbic.skl.repository.UserStmtRepository;
import com.hshbic.skl.service.UserStmtService;
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
import java.util.List;


import static com.hshbic.skl.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserStmtResource REST controller.
 *
 * @see UserStmtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class UserStmtResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    @Autowired
    private UserStmtRepository userStmtRepository;

    @Autowired
    private UserStmtService userStmtService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserStmtMockMvc;

    private UserStmt userStmt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserStmtResource userStmtResource = new UserStmtResource(userStmtService);
        this.restUserStmtMockMvc = MockMvcBuilders.standaloneSetup(userStmtResource)
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
    public static UserStmt createEntity(EntityManager em) {
        UserStmt userStmt = new UserStmt()
            .content(DEFAULT_CONTENT)
            .delFlag(DEFAULT_DEL_FLAG);
        return userStmt;
    }

    @Before
    public void initTest() {
        userStmt = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserStmt() throws Exception {
        int databaseSizeBeforeCreate = userStmtRepository.findAll().size();

        // Create the UserStmt
        restUserStmtMockMvc.perform(post("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStmt)))
            .andExpect(status().isCreated());

        // Validate the UserStmt in the database
        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeCreate + 1);
        UserStmt testUserStmt = userStmtList.get(userStmtList.size() - 1);
        assertThat(testUserStmt.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testUserStmt.isDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    public void createUserStmtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStmtRepository.findAll().size();

        // Create the UserStmt with an existing ID
        userStmt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStmtMockMvc.perform(post("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStmt)))
            .andExpect(status().isBadRequest());

        // Validate the UserStmt in the database
        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStmtRepository.findAll().size();
        // set the field null
        userStmt.setContent(null);

        // Create the UserStmt, which fails.

        restUserStmtMockMvc.perform(post("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStmt)))
            .andExpect(status().isBadRequest());

        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStmtRepository.findAll().size();
        // set the field null
        userStmt.setDelFlag(null);

        // Create the UserStmt, which fails.

        restUserStmtMockMvc.perform(post("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStmt)))
            .andExpect(status().isBadRequest());

        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserStmts() throws Exception {
        // Initialize the database
        userStmtRepository.saveAndFlush(userStmt);

        // Get all the userStmtList
        restUserStmtMockMvc.perform(get("/api/user-stmts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStmt.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserStmt() throws Exception {
        // Initialize the database
        userStmtRepository.saveAndFlush(userStmt);

        // Get the userStmt
        restUserStmtMockMvc.perform(get("/api/user-stmts/{id}", userStmt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userStmt.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserStmt() throws Exception {
        // Get the userStmt
        restUserStmtMockMvc.perform(get("/api/user-stmts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStmt() throws Exception {
        // Initialize the database
        userStmtService.save(userStmt);

        int databaseSizeBeforeUpdate = userStmtRepository.findAll().size();

        // Update the userStmt
        UserStmt updatedUserStmt = userStmtRepository.findById(userStmt.getId()).get();
        // Disconnect from session so that the updates on updatedUserStmt are not directly saved in db
        em.detach(updatedUserStmt);
        updatedUserStmt
            .content(UPDATED_CONTENT)
            .delFlag(UPDATED_DEL_FLAG);

        restUserStmtMockMvc.perform(put("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserStmt)))
            .andExpect(status().isOk());

        // Validate the UserStmt in the database
        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeUpdate);
        UserStmt testUserStmt = userStmtList.get(userStmtList.size() - 1);
        assertThat(testUserStmt.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testUserStmt.isDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingUserStmt() throws Exception {
        int databaseSizeBeforeUpdate = userStmtRepository.findAll().size();

        // Create the UserStmt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserStmtMockMvc.perform(put("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStmt)))
            .andExpect(status().isBadRequest());

        // Validate the UserStmt in the database
        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserStmt() throws Exception {
        // Initialize the database
        userStmtService.save(userStmt);

        int databaseSizeBeforeDelete = userStmtRepository.findAll().size();

        // Get the userStmt
        restUserStmtMockMvc.perform(delete("/api/user-stmts/{id}", userStmt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStmt.class);
        UserStmt userStmt1 = new UserStmt();
        userStmt1.setId(1L);
        UserStmt userStmt2 = new UserStmt();
        userStmt2.setId(userStmt1.getId());
        assertThat(userStmt1).isEqualTo(userStmt2);
        userStmt2.setId(2L);
        assertThat(userStmt1).isNotEqualTo(userStmt2);
        userStmt1.setId(null);
        assertThat(userStmt1).isNotEqualTo(userStmt2);
    }
}