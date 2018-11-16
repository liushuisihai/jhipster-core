package com.hshbic.skl.web.rest;

import com.hshbic.skl.MyappApp;

import com.hshbic.skl.domain.MyUser;
import com.hshbic.skl.repository.MyUserRepository;
import com.hshbic.skl.service.MyUserService;
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
 * Test class for the MyUserResource REST controller.
 *
 * @see MyUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class MyUserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyUserMockMvc;

    private MyUser myUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyUserResource myUserResource = new MyUserResource(myUserService);
        this.restMyUserMockMvc = MockMvcBuilders.standaloneSetup(myUserResource)
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
    public static MyUser createEntity(EntityManager em) {
        MyUser myUser = new MyUser()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .photo(DEFAULT_PHOTO)
            .token(DEFAULT_TOKEN);
        return myUser;
    }

    @Before
    public void initTest() {
        myUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyUser() throws Exception {
        int databaseSizeBeforeCreate = myUserRepository.findAll().size();

        // Create the MyUser
        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isCreated());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate + 1);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMyUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMyUser.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMyUser.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createMyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myUserRepository.findAll().size();

        // Create the MyUser with an existing ID
        myUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setUsername(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setPassword(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUserRepository.findAll().size();
        // set the field null
        myUser.setPhoto(null);

        // Create the MyUser, which fails.

        restMyUserMockMvc.perform(post("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyUsers() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get all the myUserList
        restMyUserMockMvc.perform(get("/api/my-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getMyUser() throws Exception {
        // Initialize the database
        myUserRepository.saveAndFlush(myUser);

        // Get the myUser
        restMyUserMockMvc.perform(get("/api/my-users/{id}", myUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyUser() throws Exception {
        // Get the myUser
        restMyUserMockMvc.perform(get("/api/my-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyUser() throws Exception {
        // Initialize the database
        myUserService.save(myUser);

        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Update the myUser
        MyUser updatedMyUser = myUserRepository.findById(myUser.getId()).get();
        // Disconnect from session so that the updates on updatedMyUser are not directly saved in db
        em.detach(updatedMyUser);
        updatedMyUser
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .photo(UPDATED_PHOTO)
            .token(UPDATED_TOKEN);

        restMyUserMockMvc.perform(put("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyUser)))
            .andExpect(status().isOk());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
        MyUser testMyUser = myUserList.get(myUserList.size() - 1);
        assertThat(testMyUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMyUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMyUser.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMyUser.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingMyUser() throws Exception {
        int databaseSizeBeforeUpdate = myUserRepository.findAll().size();

        // Create the MyUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyUserMockMvc.perform(put("/api/my-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myUser)))
            .andExpect(status().isBadRequest());

        // Validate the MyUser in the database
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyUser() throws Exception {
        // Initialize the database
        myUserService.save(myUser);

        int databaseSizeBeforeDelete = myUserRepository.findAll().size();

        // Get the myUser
        restMyUserMockMvc.perform(delete("/api/my-users/{id}", myUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyUser> myUserList = myUserRepository.findAll();
        assertThat(myUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyUser.class);
        MyUser myUser1 = new MyUser();
        myUser1.setId(1L);
        MyUser myUser2 = new MyUser();
        myUser2.setId(myUser1.getId());
        assertThat(myUser1).isEqualTo(myUser2);
        myUser2.setId(2L);
        assertThat(myUser1).isNotEqualTo(myUser2);
        myUser1.setId(null);
        assertThat(myUser1).isNotEqualTo(myUser2);
    }
}
