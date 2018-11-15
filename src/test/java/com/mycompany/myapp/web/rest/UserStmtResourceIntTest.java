package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.UserStmt;
import com.mycompany.myapp.repository.UserStmtRepository;
import com.mycompany.myapp.service.UserStmtService;
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
 * Test class for the UserStmtResource REST controller.
 *
 * @see UserStmtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class UserStmtResourceIntTest {

    private static final Long DEFAULT_INTENT_ID = 1L;
    private static final Long UPDATED_INTENT_ID = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "-1";
    private static final String UPDATED_STATUS = "-1B";

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
            .intentId(DEFAULT_INTENT_ID)
            .content(DEFAULT_CONTENT)
            .status(DEFAULT_STATUS);
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
        assertThat(testUserStmt.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testUserStmt.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testUserStmt.getStatus()).isEqualTo(DEFAULT_STATUS);
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
    public void checkIntentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStmtRepository.findAll().size();
        // set the field null
        userStmt.setIntentId(null);

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
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStmtRepository.findAll().size();
        // set the field null
        userStmt.setStatus(null);

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
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
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
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
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
            .intentId(UPDATED_INTENT_ID)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS);

        restUserStmtMockMvc.perform(put("/api/user-stmts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserStmt)))
            .andExpect(status().isOk());

        // Validate the UserStmt in the database
        List<UserStmt> userStmtList = userStmtRepository.findAll();
        assertThat(userStmtList).hasSize(databaseSizeBeforeUpdate);
        UserStmt testUserStmt = userStmtList.get(userStmtList.size() - 1);
        assertThat(testUserStmt.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testUserStmt.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testUserStmt.getStatus()).isEqualTo(UPDATED_STATUS);
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
