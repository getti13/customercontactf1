package com.abissinia.customercontact.web.rest;

import com.abissinia.customercontact.Application;
import com.abissinia.customercontact.domain.ContactDetails;
import com.abissinia.customercontact.repository.ContactDetailsRepository;
import com.abissinia.customercontact.repository.search.ContactDetailsSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.abissinia.customercontact.domain.enumeration.PreferedContact;
import com.abissinia.customercontact.domain.enumeration.PreferedLanguage;
import com.abissinia.customercontact.domain.enumeration.Country;

/**
 * Test class for the ContactDetailsResource REST controller.
 *
 * @see ContactDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContactDetailsResourceTest {

    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_MIDDLE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_MIDDLE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LAST_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    private static final Boolean DEFAULT_SHEBA_MILES = false;
    private static final Boolean UPDATED_SHEBA_MILES = true;
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_FAX = "SAMPLE_TEXT";
    private static final String UPDATED_FAX = "UPDATED_TEXT";

    private static final PreferedContact DEFAULT_PREFERED_CONTACT = PreferedContact.Telephone;
    private static final PreferedContact UPDATED_PREFERED_CONTACT = PreferedContact.Mobile;

    private static final PreferedLanguage DEFAULT_PREFERED_LANGUAGE = PreferedLanguage.German;
    private static final PreferedLanguage UPDATED_PREFERED_LANGUAGE = PreferedLanguage.Amharic;

    private static final Country DEFAULT_COUNTRY = Country.Germany;
    private static final Country UPDATED_COUNTRY = Country.Ethiopia;
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_CITY = "SAMPLE_TEXT";
    private static final String UPDATED_CITY = "UPDATED_TEXT";

    private static final Integer DEFAULT_ZIPCODE = 1;
    private static final Integer UPDATED_ZIPCODE = 2;
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";

    @Inject
    private ContactDetailsRepository contactDetailsRepository;

    @Inject
    private ContactDetailsSearchRepository contactDetailsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContactDetailsMockMvc;

    private ContactDetails contactDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactDetailsResource contactDetailsResource = new ContactDetailsResource();
        ReflectionTestUtils.setField(contactDetailsResource, "contactDetailsRepository", contactDetailsRepository);
        ReflectionTestUtils.setField(contactDetailsResource, "contactDetailsSearchRepository", contactDetailsSearchRepository);
        this.restContactDetailsMockMvc = MockMvcBuilders.standaloneSetup(contactDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contactDetails = new ContactDetails();
        contactDetails.setFirstName(DEFAULT_FIRST_NAME);
        contactDetails.setMiddleName(DEFAULT_MIDDLE_NAME);
        contactDetails.setLastName(DEFAULT_LAST_NAME);
        contactDetails.setTelephone(DEFAULT_TELEPHONE);
        contactDetails.setShebaMiles(DEFAULT_SHEBA_MILES);
        contactDetails.setEmail(DEFAULT_EMAIL);
        contactDetails.setFax(DEFAULT_FAX);
        contactDetails.setPreferedContact(DEFAULT_PREFERED_CONTACT);
        contactDetails.setPreferedLanguage(DEFAULT_PREFERED_LANGUAGE);
        contactDetails.setCountry(DEFAULT_COUNTRY);
        contactDetails.setAddress(DEFAULT_ADDRESS);
        contactDetails.setCity(DEFAULT_CITY);
        contactDetails.setZipcode(DEFAULT_ZIPCODE);
        contactDetails.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createContactDetails() throws Exception {
        int databaseSizeBeforeCreate = contactDetailsRepository.findAll().size();

        // Create the ContactDetails

        restContactDetailsMockMvc.perform(post("/api/contactDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactDetails)))
                .andExpect(status().isCreated());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailss = contactDetailsRepository.findAll();
        assertThat(contactDetailss).hasSize(databaseSizeBeforeCreate + 1);
        ContactDetails testContactDetails = contactDetailss.get(contactDetailss.size() - 1);
        assertThat(testContactDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContactDetails.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testContactDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContactDetails.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testContactDetails.getShebaMiles()).isEqualTo(DEFAULT_SHEBA_MILES);
        assertThat(testContactDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactDetails.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testContactDetails.getPreferedContact()).isEqualTo(DEFAULT_PREFERED_CONTACT);
        assertThat(testContactDetails.getPreferedLanguage()).isEqualTo(DEFAULT_PREFERED_LANGUAGE);
        assertThat(testContactDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testContactDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testContactDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContactDetails.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testContactDetails.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllContactDetailss() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailss
        restContactDetailsMockMvc.perform(get("/api/contactDetailss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contactDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
                .andExpect(jsonPath("$.[*].shebaMiles").value(hasItem(DEFAULT_SHEBA_MILES.booleanValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].preferedContact").value(hasItem(DEFAULT_PREFERED_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].preferedLanguage").value(hasItem(DEFAULT_PREFERED_LANGUAGE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get the contactDetails
        restContactDetailsMockMvc.perform(get("/api/contactDetailss/{id}", contactDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contactDetails.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.shebaMiles").value(DEFAULT_SHEBA_MILES.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.preferedContact").value(DEFAULT_PREFERED_CONTACT.toString()))
            .andExpect(jsonPath("$.preferedLanguage").value(DEFAULT_PREFERED_LANGUAGE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactDetails() throws Exception {
        // Get the contactDetails
        restContactDetailsMockMvc.perform(get("/api/contactDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

		int databaseSizeBeforeUpdate = contactDetailsRepository.findAll().size();

        // Update the contactDetails
        contactDetails.setFirstName(UPDATED_FIRST_NAME);
        contactDetails.setMiddleName(UPDATED_MIDDLE_NAME);
        contactDetails.setLastName(UPDATED_LAST_NAME);
        contactDetails.setTelephone(UPDATED_TELEPHONE);
        contactDetails.setShebaMiles(UPDATED_SHEBA_MILES);
        contactDetails.setEmail(UPDATED_EMAIL);
        contactDetails.setFax(UPDATED_FAX);
        contactDetails.setPreferedContact(UPDATED_PREFERED_CONTACT);
        contactDetails.setPreferedLanguage(UPDATED_PREFERED_LANGUAGE);
        contactDetails.setCountry(UPDATED_COUNTRY);
        contactDetails.setAddress(UPDATED_ADDRESS);
        contactDetails.setCity(UPDATED_CITY);
        contactDetails.setZipcode(UPDATED_ZIPCODE);
        contactDetails.setComment(UPDATED_COMMENT);
        

        restContactDetailsMockMvc.perform(put("/api/contactDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contactDetails)))
                .andExpect(status().isOk());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailss = contactDetailsRepository.findAll();
        assertThat(contactDetailss).hasSize(databaseSizeBeforeUpdate);
        ContactDetails testContactDetails = contactDetailss.get(contactDetailss.size() - 1);
        assertThat(testContactDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactDetails.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testContactDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContactDetails.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testContactDetails.getShebaMiles()).isEqualTo(UPDATED_SHEBA_MILES);
        assertThat(testContactDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactDetails.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testContactDetails.getPreferedContact()).isEqualTo(UPDATED_PREFERED_CONTACT);
        assertThat(testContactDetails.getPreferedLanguage()).isEqualTo(UPDATED_PREFERED_LANGUAGE);
        assertThat(testContactDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContactDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testContactDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContactDetails.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testContactDetails.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

		int databaseSizeBeforeDelete = contactDetailsRepository.findAll().size();

        // Get the contactDetails
        restContactDetailsMockMvc.perform(delete("/api/contactDetailss/{id}", contactDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactDetails> contactDetailss = contactDetailsRepository.findAll();
        assertThat(contactDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
