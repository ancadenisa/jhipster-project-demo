package com.crispico.absence_management.web.rest;

import com.crispico.absence_management.AbsenceManagementApp;

import com.crispico.absence_management.domain.Cherry;
import com.crispico.absence_management.repository.CherryRepository;
import com.crispico.absence_management.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.crispico.absence_management.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CherryResource REST controller.
 *
 * @see CherryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbsenceManagementApp.class)
public class CherryResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CherryRepository cherryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCherryMockMvc;

    private Cherry cherry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CherryResource cherryResource = new CherryResource(cherryRepository);
        this.restCherryMockMvc = MockMvcBuilders.standaloneSetup(cherryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cherry createEntity(EntityManager em) {
        Cherry cherry = new Cherry()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .date(DEFAULT_DATE);
        return cherry;
    }

    @Before
    public void initTest() {
        cherry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCherry() throws Exception {
        int databaseSizeBeforeCreate = cherryRepository.findAll().size();

        // Create the Cherry
        restCherryMockMvc.perform(post("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cherry)))
            .andExpect(status().isCreated());

        // Validate the Cherry in the database
        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeCreate + 1);
        Cherry testCherry = cherryList.get(cherryList.size() - 1);
        assertThat(testCherry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCherry.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCherry.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createCherryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cherryRepository.findAll().size();

        // Create the Cherry with an existing ID
        cherry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCherryMockMvc.perform(post("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cherry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cherryRepository.findAll().size();
        // set the field null
        cherry.setTitle(null);

        // Create the Cherry, which fails.

        restCherryMockMvc.perform(post("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cherry)))
            .andExpect(status().isBadRequest());

        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = cherryRepository.findAll().size();
        // set the field null
        cherry.setContent(null);

        // Create the Cherry, which fails.

        restCherryMockMvc.perform(post("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cherry)))
            .andExpect(status().isBadRequest());

        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cherryRepository.findAll().size();
        // set the field null
        cherry.setDate(null);

        // Create the Cherry, which fails.

        restCherryMockMvc.perform(post("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cherry)))
            .andExpect(status().isBadRequest());

        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCherries() throws Exception {
        // Initialize the database
        cherryRepository.saveAndFlush(cherry);

        // Get all the cherryList
        restCherryMockMvc.perform(get("/api/cherries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cherry.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getCherry() throws Exception {
        // Initialize the database
        cherryRepository.saveAndFlush(cherry);

        // Get the cherry
        restCherryMockMvc.perform(get("/api/cherries/{id}", cherry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cherry.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingCherry() throws Exception {
        // Get the cherry
        restCherryMockMvc.perform(get("/api/cherries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCherry() throws Exception {
        // Initialize the database
        cherryRepository.saveAndFlush(cherry);
        int databaseSizeBeforeUpdate = cherryRepository.findAll().size();

        // Update the cherry
        Cherry updatedCherry = cherryRepository.findOne(cherry.getId());
        updatedCherry
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);

        restCherryMockMvc.perform(put("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCherry)))
            .andExpect(status().isOk());

        // Validate the Cherry in the database
        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeUpdate);
        Cherry testCherry = cherryList.get(cherryList.size() - 1);
        assertThat(testCherry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCherry.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCherry.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCherry() throws Exception {
        int databaseSizeBeforeUpdate = cherryRepository.findAll().size();

        // Create the Cherry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCherryMockMvc.perform(put("/api/cherries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cherry)))
            .andExpect(status().isCreated());

        // Validate the Cherry in the database
        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCherry() throws Exception {
        // Initialize the database
        cherryRepository.saveAndFlush(cherry);
        int databaseSizeBeforeDelete = cherryRepository.findAll().size();

        // Get the cherry
        restCherryMockMvc.perform(delete("/api/cherries/{id}", cherry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cherry> cherryList = cherryRepository.findAll();
        assertThat(cherryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cherry.class);
        Cherry cherry1 = new Cherry();
        cherry1.setId(1L);
        Cherry cherry2 = new Cherry();
        cherry2.setId(cherry1.getId());
        assertThat(cherry1).isEqualTo(cherry2);
        cherry2.setId(2L);
        assertThat(cherry1).isNotEqualTo(cherry2);
        cherry1.setId(null);
        assertThat(cherry1).isNotEqualTo(cherry2);
    }
}
