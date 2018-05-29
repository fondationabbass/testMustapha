package com.bdi.fondation.web.rest;

import com.bdi.fondation.TestMustaphaApp;

import com.bdi.fondation.domain.Pret;
import com.bdi.fondation.repository.PretRepository;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;

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

import static com.bdi.fondation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PretResource REST controller.
 *
 * @see PretResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestMustaphaApp.class)
public class PretResourceIntTest {

    private static final String DEFAULT_TYP_PRET = "AAAAAAAAAA";
    private static final String UPDATED_TYP_PRET = "BBBBBBBBBB";

    private static final Double DEFAULT_MONT_AACCORD = 1D;
    private static final Double UPDATED_MONT_AACCORD = 2D;

    private static final Double DEFAULT_MONT_DEBLOQ = 1D;
    private static final Double UPDATED_MONT_DEBLOQ = 2D;

    private static final Integer DEFAULT_NBR_ECHEANCE = 1;
    private static final Integer UPDATED_NBR_ECHEANCE = 2;

    private static final String DEFAULT_PERIODICITE = "AAAAAAAAAA";
    private static final String UPDATED_PERIODICITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_MISE_PLACE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MISE_PLACE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PREMIERE_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PREMIERE_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DERNIERE_ECHEANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIERE_ECHEANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DERNIER_DEBLOQ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIER_DEBLOQ = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final Double DEFAULT_ENCOURS = 1D;
    private static final Double UPDATED_ENCOURS = 2D;

    private static final String DEFAULT_USER_INITIAL = "AAAAAAAAAA";
    private static final String UPDATED_USER_INITIAL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_DECIDEUR = "AAAAAAAAAA";
    private static final String UPDATED_USER_DECIDEUR = "BBBBBBBBBB";

    private static final String DEFAULT_USER_DEBLOQ = "AAAAAAAAAA";
    private static final String UPDATED_USER_DEBLOQ = "BBBBBBBBBB";

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPretMockMvc;

    private Pret pret;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PretResource pretResource = new PretResource(pretRepository);
        this.restPretMockMvc = MockMvcBuilders.standaloneSetup(pretResource)
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
    public static Pret createEntity(EntityManager em) {
        Pret pret = new Pret()
            .typPret(DEFAULT_TYP_PRET)
            .montAaccord(DEFAULT_MONT_AACCORD)
            .montDebloq(DEFAULT_MONT_DEBLOQ)
            .nbrEcheance(DEFAULT_NBR_ECHEANCE)
            .periodicite(DEFAULT_PERIODICITE)
            .dateMisePlace(DEFAULT_DATE_MISE_PLACE)
            .datePremiereEcheance(DEFAULT_DATE_PREMIERE_ECHEANCE)
            .dateDerniereEcheance(DEFAULT_DATE_DERNIERE_ECHEANCE)
            .dateDernierDebloq(DEFAULT_DATE_DERNIER_DEBLOQ)
            .etat(DEFAULT_ETAT)
            .encours(DEFAULT_ENCOURS)
            .userInitial(DEFAULT_USER_INITIAL)
            .userDecideur(DEFAULT_USER_DECIDEUR)
            .userDebloq(DEFAULT_USER_DEBLOQ);
        return pret;
    }

    @Before
    public void initTest() {
        pret = createEntity(em);
    }

    @Test
    @Transactional
    public void createPret() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate + 1);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getTypPret()).isEqualTo(DEFAULT_TYP_PRET);
        assertThat(testPret.getMontAaccord()).isEqualTo(DEFAULT_MONT_AACCORD);
        assertThat(testPret.getMontDebloq()).isEqualTo(DEFAULT_MONT_DEBLOQ);
        assertThat(testPret.getNbrEcheance()).isEqualTo(DEFAULT_NBR_ECHEANCE);
        assertThat(testPret.getPeriodicite()).isEqualTo(DEFAULT_PERIODICITE);
        assertThat(testPret.getDateMisePlace()).isEqualTo(DEFAULT_DATE_MISE_PLACE);
        assertThat(testPret.getDatePremiereEcheance()).isEqualTo(DEFAULT_DATE_PREMIERE_ECHEANCE);
        assertThat(testPret.getDateDerniereEcheance()).isEqualTo(DEFAULT_DATE_DERNIERE_ECHEANCE);
        assertThat(testPret.getDateDernierDebloq()).isEqualTo(DEFAULT_DATE_DERNIER_DEBLOQ);
        assertThat(testPret.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testPret.getEncours()).isEqualTo(DEFAULT_ENCOURS);
        assertThat(testPret.getUserInitial()).isEqualTo(DEFAULT_USER_INITIAL);
        assertThat(testPret.getUserDecideur()).isEqualTo(DEFAULT_USER_DECIDEUR);
        assertThat(testPret.getUserDebloq()).isEqualTo(DEFAULT_USER_DEBLOQ);
    }

    @Test
    @Transactional
    public void createPretWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret with an existing ID
        pret.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrets() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList
        restPretMockMvc.perform(get("/api/prets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pret.getId().intValue())))
            .andExpect(jsonPath("$.[*].typPret").value(hasItem(DEFAULT_TYP_PRET.toString())))
            .andExpect(jsonPath("$.[*].montAaccord").value(hasItem(DEFAULT_MONT_AACCORD.doubleValue())))
            .andExpect(jsonPath("$.[*].montDebloq").value(hasItem(DEFAULT_MONT_DEBLOQ.doubleValue())))
            .andExpect(jsonPath("$.[*].nbrEcheance").value(hasItem(DEFAULT_NBR_ECHEANCE)))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE.toString())))
            .andExpect(jsonPath("$.[*].dateMisePlace").value(hasItem(DEFAULT_DATE_MISE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].datePremiereEcheance").value(hasItem(DEFAULT_DATE_PREMIERE_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].dateDerniereEcheance").value(hasItem(DEFAULT_DATE_DERNIERE_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].dateDernierDebloq").value(hasItem(DEFAULT_DATE_DERNIER_DEBLOQ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].encours").value(hasItem(DEFAULT_ENCOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].userInitial").value(hasItem(DEFAULT_USER_INITIAL.toString())))
            .andExpect(jsonPath("$.[*].userDecideur").value(hasItem(DEFAULT_USER_DECIDEUR.toString())))
            .andExpect(jsonPath("$.[*].userDebloq").value(hasItem(DEFAULT_USER_DEBLOQ.toString())));
    }

    @Test
    @Transactional
    public void getPret() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", pret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pret.getId().intValue()))
            .andExpect(jsonPath("$.typPret").value(DEFAULT_TYP_PRET.toString()))
            .andExpect(jsonPath("$.montAaccord").value(DEFAULT_MONT_AACCORD.doubleValue()))
            .andExpect(jsonPath("$.montDebloq").value(DEFAULT_MONT_DEBLOQ.doubleValue()))
            .andExpect(jsonPath("$.nbrEcheance").value(DEFAULT_NBR_ECHEANCE))
            .andExpect(jsonPath("$.periodicite").value(DEFAULT_PERIODICITE.toString()))
            .andExpect(jsonPath("$.dateMisePlace").value(DEFAULT_DATE_MISE_PLACE.toString()))
            .andExpect(jsonPath("$.datePremiereEcheance").value(DEFAULT_DATE_PREMIERE_ECHEANCE.toString()))
            .andExpect(jsonPath("$.dateDerniereEcheance").value(DEFAULT_DATE_DERNIERE_ECHEANCE.toString()))
            .andExpect(jsonPath("$.dateDernierDebloq").value(DEFAULT_DATE_DERNIER_DEBLOQ.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.encours").value(DEFAULT_ENCOURS.doubleValue()))
            .andExpect(jsonPath("$.userInitial").value(DEFAULT_USER_INITIAL.toString()))
            .andExpect(jsonPath("$.userDecideur").value(DEFAULT_USER_DECIDEUR.toString()))
            .andExpect(jsonPath("$.userDebloq").value(DEFAULT_USER_DEBLOQ.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPret() throws Exception {
        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePret() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);
        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Update the pret
        Pret updatedPret = pretRepository.findOne(pret.getId());
        // Disconnect from session so that the updates on updatedPret are not directly saved in db
        em.detach(updatedPret);
        updatedPret
            .typPret(UPDATED_TYP_PRET)
            .montAaccord(UPDATED_MONT_AACCORD)
            .montDebloq(UPDATED_MONT_DEBLOQ)
            .nbrEcheance(UPDATED_NBR_ECHEANCE)
            .periodicite(UPDATED_PERIODICITE)
            .dateMisePlace(UPDATED_DATE_MISE_PLACE)
            .datePremiereEcheance(UPDATED_DATE_PREMIERE_ECHEANCE)
            .dateDerniereEcheance(UPDATED_DATE_DERNIERE_ECHEANCE)
            .dateDernierDebloq(UPDATED_DATE_DERNIER_DEBLOQ)
            .etat(UPDATED_ETAT)
            .encours(UPDATED_ENCOURS)
            .userInitial(UPDATED_USER_INITIAL)
            .userDecideur(UPDATED_USER_DECIDEUR)
            .userDebloq(UPDATED_USER_DEBLOQ);

        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPret)))
            .andExpect(status().isOk());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getTypPret()).isEqualTo(UPDATED_TYP_PRET);
        assertThat(testPret.getMontAaccord()).isEqualTo(UPDATED_MONT_AACCORD);
        assertThat(testPret.getMontDebloq()).isEqualTo(UPDATED_MONT_DEBLOQ);
        assertThat(testPret.getNbrEcheance()).isEqualTo(UPDATED_NBR_ECHEANCE);
        assertThat(testPret.getPeriodicite()).isEqualTo(UPDATED_PERIODICITE);
        assertThat(testPret.getDateMisePlace()).isEqualTo(UPDATED_DATE_MISE_PLACE);
        assertThat(testPret.getDatePremiereEcheance()).isEqualTo(UPDATED_DATE_PREMIERE_ECHEANCE);
        assertThat(testPret.getDateDerniereEcheance()).isEqualTo(UPDATED_DATE_DERNIERE_ECHEANCE);
        assertThat(testPret.getDateDernierDebloq()).isEqualTo(UPDATED_DATE_DERNIER_DEBLOQ);
        assertThat(testPret.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testPret.getEncours()).isEqualTo(UPDATED_ENCOURS);
        assertThat(testPret.getUserInitial()).isEqualTo(UPDATED_USER_INITIAL);
        assertThat(testPret.getUserDecideur()).isEqualTo(UPDATED_USER_DECIDEUR);
        assertThat(testPret.getUserDebloq()).isEqualTo(UPDATED_USER_DEBLOQ);
    }

    @Test
    @Transactional
    public void updateNonExistingPret() throws Exception {
        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Create the Pret

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePret() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);
        int databaseSizeBeforeDelete = pretRepository.findAll().size();

        // Get the pret
        restPretMockMvc.perform(delete("/api/prets/{id}", pret.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pret.class);
        Pret pret1 = new Pret();
        pret1.setId(1L);
        Pret pret2 = new Pret();
        pret2.setId(pret1.getId());
        assertThat(pret1).isEqualTo(pret2);
        pret2.setId(2L);
        assertThat(pret1).isNotEqualTo(pret2);
        pret1.setId(null);
        assertThat(pret1).isNotEqualTo(pret2);
    }
}
