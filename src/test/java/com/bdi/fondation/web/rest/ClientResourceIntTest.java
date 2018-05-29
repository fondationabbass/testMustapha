package com.bdi.fondation.web.rest;

import com.bdi.fondation.TestMustaphaApp;

import com.bdi.fondation.domain.Client;
import com.bdi.fondation.repository.ClientRepository;
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
 * Test class for the ClientResource REST controller.
 *
 * @see ClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestMustaphaApp.class)
public class ClientResourceIntTest {

    private static final LocalDate DEFAULT_DATE_CREAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREAT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_RESID = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_RESID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_RESID = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_RESID = "BBBBBBBBBB";

    private static final String DEFAULT_ARROND_RESID = "AAAAAAAAAA";
    private static final String UPDATED_ARROND_RESID = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PERSONNE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PERSONNE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_PERSONNE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_TEL_PERSONNE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS_PERSONNE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_PERSONNE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CLIENT = "BBBBBBBBBB";

    private static final Double DEFAULT_POINTS_FIDEL = 1D;
    private static final Double UPDATED_POINTS_FIDEL = 2D;

    private static final LocalDate DEFAULT_DATE_MAJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MAJ = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientMockMvc;

    private Client client;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientResource clientResource = new ClientResource(clientRepository);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
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
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .dateCreat(DEFAULT_DATE_CREAT)
            .lieuResid(DEFAULT_LIEU_RESID)
            .typeResid(DEFAULT_TYPE_RESID)
            .arrondResid(DEFAULT_ARROND_RESID)
            .nomPersonneContact(DEFAULT_NOM_PERSONNE_CONTACT)
            .telPersonneContact(DEFAULT_TEL_PERSONNE_CONTACT)
            .adressPersonneContact(DEFAULT_ADRESS_PERSONNE_CONTACT)
            .typeClient(DEFAULT_TYPE_CLIENT)
            .pointsFidel(DEFAULT_POINTS_FIDEL)
            .dateMaj(DEFAULT_DATE_MAJ);
        return client;
    }

    @Before
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDateCreat()).isEqualTo(DEFAULT_DATE_CREAT);
        assertThat(testClient.getLieuResid()).isEqualTo(DEFAULT_LIEU_RESID);
        assertThat(testClient.getTypeResid()).isEqualTo(DEFAULT_TYPE_RESID);
        assertThat(testClient.getArrondResid()).isEqualTo(DEFAULT_ARROND_RESID);
        assertThat(testClient.getNomPersonneContact()).isEqualTo(DEFAULT_NOM_PERSONNE_CONTACT);
        assertThat(testClient.getTelPersonneContact()).isEqualTo(DEFAULT_TEL_PERSONNE_CONTACT);
        assertThat(testClient.getAdressPersonneContact()).isEqualTo(DEFAULT_ADRESS_PERSONNE_CONTACT);
        assertThat(testClient.getTypeClient()).isEqualTo(DEFAULT_TYPE_CLIENT);
        assertThat(testClient.getPointsFidel()).isEqualTo(DEFAULT_POINTS_FIDEL);
        assertThat(testClient.getDateMaj()).isEqualTo(DEFAULT_DATE_MAJ);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreat").value(hasItem(DEFAULT_DATE_CREAT.toString())))
            .andExpect(jsonPath("$.[*].lieuResid").value(hasItem(DEFAULT_LIEU_RESID.toString())))
            .andExpect(jsonPath("$.[*].typeResid").value(hasItem(DEFAULT_TYPE_RESID.toString())))
            .andExpect(jsonPath("$.[*].arrondResid").value(hasItem(DEFAULT_ARROND_RESID.toString())))
            .andExpect(jsonPath("$.[*].nomPersonneContact").value(hasItem(DEFAULT_NOM_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].telPersonneContact").value(hasItem(DEFAULT_TEL_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].adressPersonneContact").value(hasItem(DEFAULT_ADRESS_PERSONNE_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].typeClient").value(hasItem(DEFAULT_TYPE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].pointsFidel").value(hasItem(DEFAULT_POINTS_FIDEL.doubleValue())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())));
    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.dateCreat").value(DEFAULT_DATE_CREAT.toString()))
            .andExpect(jsonPath("$.lieuResid").value(DEFAULT_LIEU_RESID.toString()))
            .andExpect(jsonPath("$.typeResid").value(DEFAULT_TYPE_RESID.toString()))
            .andExpect(jsonPath("$.arrondResid").value(DEFAULT_ARROND_RESID.toString()))
            .andExpect(jsonPath("$.nomPersonneContact").value(DEFAULT_NOM_PERSONNE_CONTACT.toString()))
            .andExpect(jsonPath("$.telPersonneContact").value(DEFAULT_TEL_PERSONNE_CONTACT.toString()))
            .andExpect(jsonPath("$.adressPersonneContact").value(DEFAULT_ADRESS_PERSONNE_CONTACT.toString()))
            .andExpect(jsonPath("$.typeClient").value(DEFAULT_TYPE_CLIENT.toString()))
            .andExpect(jsonPath("$.pointsFidel").value(DEFAULT_POINTS_FIDEL.doubleValue()))
            .andExpect(jsonPath("$.dateMaj").value(DEFAULT_DATE_MAJ.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findOne(client.getId());
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .dateCreat(UPDATED_DATE_CREAT)
            .lieuResid(UPDATED_LIEU_RESID)
            .typeResid(UPDATED_TYPE_RESID)
            .arrondResid(UPDATED_ARROND_RESID)
            .nomPersonneContact(UPDATED_NOM_PERSONNE_CONTACT)
            .telPersonneContact(UPDATED_TEL_PERSONNE_CONTACT)
            .adressPersonneContact(UPDATED_ADRESS_PERSONNE_CONTACT)
            .typeClient(UPDATED_TYPE_CLIENT)
            .pointsFidel(UPDATED_POINTS_FIDEL)
            .dateMaj(UPDATED_DATE_MAJ);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDateCreat()).isEqualTo(UPDATED_DATE_CREAT);
        assertThat(testClient.getLieuResid()).isEqualTo(UPDATED_LIEU_RESID);
        assertThat(testClient.getTypeResid()).isEqualTo(UPDATED_TYPE_RESID);
        assertThat(testClient.getArrondResid()).isEqualTo(UPDATED_ARROND_RESID);
        assertThat(testClient.getNomPersonneContact()).isEqualTo(UPDATED_NOM_PERSONNE_CONTACT);
        assertThat(testClient.getTelPersonneContact()).isEqualTo(UPDATED_TEL_PERSONNE_CONTACT);
        assertThat(testClient.getAdressPersonneContact()).isEqualTo(UPDATED_ADRESS_PERSONNE_CONTACT);
        assertThat(testClient.getTypeClient()).isEqualTo(UPDATED_TYPE_CLIENT);
        assertThat(testClient.getPointsFidel()).isEqualTo(UPDATED_POINTS_FIDEL);
        assertThat(testClient.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);
        client2.setId(2L);
        assertThat(client1).isNotEqualTo(client2);
        client1.setId(null);
        assertThat(client1).isNotEqualTo(client2);
    }
}
