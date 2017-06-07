package io.vp.projects.web.rest;

import io.vp.projects.SchedulyApp;

import io.vp.projects.domain.Invitee;
import io.vp.projects.repository.InviteeRepository;

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
 * Test class for the InviteeResource REST controller.
 *
 * @see InviteeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchedulyApp.class)
public class InviteeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_CONTACT = "AAAAA";
    private static final String UPDATED_CONTACT = "BBBBB";

    @Inject
    private InviteeRepository inviteeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInviteeMockMvc;

    private Invitee invitee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InviteeResource inviteeResource = new InviteeResource();
        ReflectionTestUtils.setField(inviteeResource, "inviteeRepository", inviteeRepository);
        this.restInviteeMockMvc = MockMvcBuilders.standaloneSetup(inviteeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invitee createEntity(EntityManager em) {
        Invitee invitee = new Invitee()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .contact(DEFAULT_CONTACT);
        return invitee;
    }

    @Before
    public void initTest() {
        invitee = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitee() throws Exception {
        int databaseSizeBeforeCreate = inviteeRepository.findAll().size();

        // Create the Invite

        restInviteeMockMvc.perform(post("/api/invitees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitee)))
                .andExpect(status().isCreated());

        // Validate the Invitee in the database
        List<Invitee> invitees = inviteeRepository.findAll();
        assertThat(invitees).hasSize(databaseSizeBeforeCreate + 1);
        Invitee testInvitee = invitees.get(invitees.size() - 1);
        assertThat(testInvitee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvitee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInvitee.getContact()).isEqualTo(DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inviteeRepository.findAll().size();
        // set the field null
        invitee.setName(null);

        // Create the Invitee, which fails.

        restInviteeMockMvc.perform(post("/api/invitees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitee)))
                .andExpect(status().isBadRequest());

        List<Invitee> invitees = inviteeRepository.findAll();
        assertThat(invitees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = inviteeRepository.findAll().size();
        // set the field null
        invitee.setEmail(null);

        // Create the Invitee, which fails.

        restInviteeMockMvc.perform(post("/api/invitees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitee)))
                .andExpect(status().isBadRequest());

        List<Invitee> invitees = inviteeRepository.findAll();
        assertThat(invitees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitees() throws Exception {
        // Initialize the database
        inviteeRepository.saveAndFlush(invitee);

        // Get all the invitees
        restInviteeMockMvc.perform(get("/api/invitees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(invitee.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())));
    }

    @Test
    @Transactional
    public void getInvitee() throws Exception {
        // Initialize the database
        inviteeRepository.saveAndFlush(invitee);

        // Get the invitee
        restInviteeMockMvc.perform(get("/api/invitees/{id}", invitee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invitee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvitee() throws Exception {
        // Get the invitee
        restInviteeMockMvc.perform(get("/api/invitees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitee() throws Exception {
        // Initialize the database
        inviteeRepository.saveAndFlush(invitee);
        int databaseSizeBeforeUpdate = inviteeRepository.findAll().size();

        // Update the invitee
        Invitee updatedInvitee = inviteeRepository.findOne(invitee.getId());
        updatedInvitee
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .contact(UPDATED_CONTACT);

        restInviteeMockMvc.perform(put("/api/invitees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInvitee)))
                .andExpect(status().isOk());

        // Validate the Invitee in the database
        List<Invitee> invitees = inviteeRepository.findAll();
        assertThat(invitees).hasSize(databaseSizeBeforeUpdate);
        Invitee testInvitee = invitees.get(invitees.size() - 1);
        assertThat(testInvitee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvitee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInvitee.getContact()).isEqualTo(UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void deleteInvitee() throws Exception {
        // Initialize the database
        inviteeRepository.saveAndFlush(invitee);
        int databaseSizeBeforeDelete = inviteeRepository.findAll().size();

        // Get the invitee
        restInviteeMockMvc.perform(delete("/api/invitees/{id}", invitee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Invitee> invitees = inviteeRepository.findAll();
        assertThat(invitees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
