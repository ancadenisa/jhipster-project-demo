package com.crispico.absence_management.web.rest;

import com.crispico.absence_management.AbsenceManagementApp;

import com.crispico.absence_management.domain.Berry;
import com.crispico.absence_management.repository.BerryRepository;
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
 * Test class for the BerryResource REST controller.
 *
 * @see BerryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbsenceManagementApp.class)
public class BerryResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BerryRepository berryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBerryMockMvc;

    private Berry berry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BerryResource berryResource = new BerryResource(berryRepository);
        this.restBerryMockMvc = MockMvcBuilders.standaloneSetup(berryResource)
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
    public static Berry createEntity(EntityManager em) {
        Berry berry = new Berry()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .date(DEFAULT_DATE);
        return berry;
    }

    @Before
    public void initTest() {
        berry = createEntity(em);
    }

    @Test
    @Transactional
    public void createBerry() throws Exception {
        int databaseSizeBeforeCreate = berryRepository.findAll().size();

        // Create the Berry
        restBerryMockMvc.perform(post("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berry)))
            .andExpect(status().isCreated());

        // Validate the Berry in the database
        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeCreate + 1);
        Berry testBerry = berryList.get(berryList.size() - 1);
        assertThat(testBerry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBerry.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBerry.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createBerryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = berryRepository.findAll().size();

        // Create the Berry with an existing ID
        berry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBerryMockMvc.perform(post("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = berryRepository.findAll().size();
        // set the field null
        berry.setTitle(null);

        // Create the Berry, which fails.

        restBerryMockMvc.perform(post("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berry)))
            .andExpect(status().isBadRequest());

        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = berryRepository.findAll().size();
        // set the field null
        berry.setContent(null);

        // Create the Berry, which fails.

        restBerryMockMvc.perform(post("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berry)))
            .andExpect(status().isBadRequest());

        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = berryRepository.findAll().size();
        // set the field null
        berry.setDate(null);

        // Create the Berry, which fails.

        restBerryMockMvc.perform(post("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berry)))
            .andExpect(status().isBadRequest());

        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBerries() throws Exception {
        // Initialize the database
        berryRepository.saveAndFlush(berry);

        // Get all the berryList
        restBerryMockMvc.perform(get("/api/berries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(berry.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getBerry() throws Exception {
        // Initialize the database
        berryRepository.saveAndFlush(berry);

        // Get the berry
        restBerryMockMvc.perform(get("/api/berries/{id}", berry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(berry.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingBerry() throws Exception {
        // Get the berry
        restBerryMockMvc.perform(get("/api/berries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBerry() throws Exception {
        // Initialize the database
        berryRepository.saveAndFlush(berry);
        int databaseSizeBeforeUpdate = berryRepository.findAll().size();

        // Update the berry
        Berry updatedBerry = berryRepository.findOne(berry.getId());
        updatedBerry
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);

        restBerryMockMvc.perform(put("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBerry)))
            .andExpect(status().isOk());

        // Validate the Berry in the database
        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeUpdate);
        Berry testBerry = berryList.get(berryList.size() - 1);
        assertThat(testBerry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBerry.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBerry.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBerry() throws Exception {
        int databaseSizeBeforeUpdate = berryRepository.findAll().size();

        // Create the Berry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBerryMockMvc.perform(put("/api/berries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(berry)))
            .andExpect(status().isCreated());

        // Validate the Berry in the database
        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBerry() throws Exception {
        // Initialize the database
        berryRepository.saveAndFlush(berry);
        int databaseSizeBeforeDelete = berryRepository.findAll().size();

        // Get the berry
        restBerryMockMvc.perform(delete("/api/berries/{id}", berry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Berry> berryList = berryRepository.findAll();
        assertThat(berryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Berry.class);
        Berry berry1 = new Berry();
        berry1.setId(1L);
        Berry berry2 = new Berry();
        berry2.setId(berry1.getId());
        assertThat(berry1).isEqualTo(berry2);
        berry2.setId(2L);
        assertThat(berry1).isNotEqualTo(berry2);
        berry1.setId(null);
        assertThat(berry1).isNotEqualTo(berry2);
    }
}
