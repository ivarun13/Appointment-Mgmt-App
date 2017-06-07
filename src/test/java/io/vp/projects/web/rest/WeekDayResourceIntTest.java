package io.vp.projects.web.rest;

import io.vp.projects.SchedulyApp;

import io.vp.projects.domain.WeekDay;
import io.vp.projects.repository.WeekDayRepository;
import io.vp.projects.service.WeekDayService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeekDayResource REST controller.
 *
 * @see WeekDayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchedulyApp.class)
public class WeekDayResourceIntTest {

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final Integer DEFAULT_START_LIMIT = 1;
    private static final Integer UPDATED_START_LIMIT = 2;

    private static final Integer DEFAULT_END_LIMIT = 1;
    private static final Integer UPDATED_END_LIMIT = 2;

    @Inject
    private WeekDayRepository weekDayRepository;

    @Inject
    private WeekDayService weekDayService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWeekDayMockMvc;

    private WeekDay weekDay;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WeekDayResource weekDayResource = new WeekDayResource();
        ReflectionTestUtils.setField(weekDayResource, "weekDayService", weekDayService);
        this.restWeekDayMockMvc = MockMvcBuilders.standaloneSetup(weekDayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeekDay createEntity(EntityManager em) {
        WeekDay weekDay = new WeekDay()
                .available(DEFAULT_AVAILABLE)
                .startLimit(DEFAULT_START_LIMIT)
                .endLimit(DEFAULT_END_LIMIT);
        return weekDay;
    }

    @Before
    public void initTest() {
        weekDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeekDay() throws Exception {
        int databaseSizeBeforeCreate = weekDayRepository.findAll().size();

        // Create the WeekDay

        restWeekDayMockMvc.perform(post("/api/week-days")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(weekDay)))
                .andExpect(status().isCreated());

        // Validate the WeekDay in the database
        List<WeekDay> weekDays = weekDayRepository.findAll();
        assertThat(weekDays).hasSize(databaseSizeBeforeCreate + 1);
        WeekDay testWeekDay = weekDays.get(weekDays.size() - 1);
        assertThat(testWeekDay.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testWeekDay.getStartLimit()).isEqualTo(DEFAULT_START_LIMIT);
        assertThat(testWeekDay.getEndLimit()).isEqualTo(DEFAULT_END_LIMIT);
    }

    @Test
    @Transactional
    public void getAllWeekDays() throws Exception {
        // Initialize the database
        weekDayRepository.saveAndFlush(weekDay);

        // Get all the weekDays
        restWeekDayMockMvc.perform(get("/api/week-days?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(weekDay.getId().intValue())))
                .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].startLimit").value(hasItem(DEFAULT_START_LIMIT)))
                .andExpect(jsonPath("$.[*].endLimit").value(hasItem(DEFAULT_END_LIMIT)));
    }

    @Test
    @Transactional
    public void getWeekDay() throws Exception {
        // Initialize the database
        weekDayRepository.saveAndFlush(weekDay);

        // Get the weekDay
        restWeekDayMockMvc.perform(get("/api/week-days/{id}", weekDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weekDay.getId().intValue()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.startLimit").value(DEFAULT_START_LIMIT))
            .andExpect(jsonPath("$.endLimit").value(DEFAULT_END_LIMIT));
    }

    @Test
    @Transactional
    public void getNonExistingWeekDay() throws Exception {
        // Get the weekDay
        restWeekDayMockMvc.perform(get("/api/week-days/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeekDay() throws Exception {
        // Initialize the database
        weekDayService.save(weekDay);

        int databaseSizeBeforeUpdate = weekDayRepository.findAll().size();

        // Update the weekDay
        WeekDay updatedWeekDay = weekDayRepository.findOne(weekDay.getId());
        updatedWeekDay
                .available(UPDATED_AVAILABLE)
                .startLimit(UPDATED_START_LIMIT)
                .endLimit(UPDATED_END_LIMIT);

        restWeekDayMockMvc.perform(put("/api/week-days")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWeekDay)))
                .andExpect(status().isOk());

        // Validate the WeekDay in the database
        List<WeekDay> weekDays = weekDayRepository.findAll();
        assertThat(weekDays).hasSize(databaseSizeBeforeUpdate);
        WeekDay testWeekDay = weekDays.get(weekDays.size() - 1);
        assertThat(testWeekDay.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testWeekDay.getStartLimit()).isEqualTo(UPDATED_START_LIMIT);
        assertThat(testWeekDay.getEndLimit()).isEqualTo(UPDATED_END_LIMIT);
    }

    @Test
    @Transactional
    public void deleteWeekDay() throws Exception {
        // Initialize the database
        weekDayService.save(weekDay);

        int databaseSizeBeforeDelete = weekDayRepository.findAll().size();

        // Get the weekDay
        restWeekDayMockMvc.perform(delete("/api/week-days/{id}", weekDay.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<WeekDay> weekDays = weekDayRepository.findAll();
        assertThat(weekDays).hasSize(databaseSizeBeforeDelete - 1);
    }
}
