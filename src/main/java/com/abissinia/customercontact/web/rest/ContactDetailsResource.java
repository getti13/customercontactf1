package com.abissinia.customercontact.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.abissinia.customercontact.domain.ContactDetails;
import com.abissinia.customercontact.repository.ContactDetailsRepository;
import com.abissinia.customercontact.repository.search.ContactDetailsSearchRepository;
import com.abissinia.customercontact.web.rest.util.HeaderUtil;
import com.abissinia.customercontact.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ContactDetails.
 */
@RestController
@RequestMapping("/api")
public class ContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsResource.class);

    @Inject
    private ContactDetailsRepository contactDetailsRepository;

    @Inject
    private ContactDetailsSearchRepository contactDetailsSearchRepository;

    /**
     * POST  /contactDetailss -> Create a new contactDetails.
     */
    @RequestMapping(value = "/contactDetailss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDetails> createContactDetails(@RequestBody ContactDetails contactDetails) throws URISyntaxException {
        log.debug("REST request to save ContactDetails : {}", contactDetails);
        if (contactDetails.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new contactDetails cannot already have an ID").body(null);
        }
        ContactDetails result = contactDetailsRepository.save(contactDetails);
        contactDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/contactDetailss/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("contactDetails", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /contactDetailss -> Updates an existing contactDetails.
     */
    @RequestMapping(value = "/contactDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDetails> updateContactDetails(@RequestBody ContactDetails contactDetails) throws URISyntaxException {
        log.debug("REST request to update ContactDetails : {}", contactDetails);
        if (contactDetails.getId() == null) {
            return createContactDetails(contactDetails);
        }
        ContactDetails result = contactDetailsRepository.save(contactDetails);
        contactDetailsSearchRepository.save(contactDetails);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("contactDetails", contactDetails.getId().toString()))
                .body(result);
    }

    /**
     * GET  /contactDetailss -> get all the contactDetailss.
     */
    @RequestMapping(value = "/contactDetailss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ContactDetails>> getAllContactDetailss(Pageable pageable)
        throws URISyntaxException {
        Page<ContactDetails> page = contactDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contactDetailss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contactDetailss/:id -> get the "id" contactDetails.
     */
    @RequestMapping(value = "/contactDetailss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContactDetails> getContactDetails(@PathVariable Long id) {
        log.debug("REST request to get ContactDetails : {}", id);
        return Optional.ofNullable(contactDetailsRepository.findOne(id))
            .map(contactDetails -> new ResponseEntity<>(
                contactDetails,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contactDetailss/:id -> delete the "id" contactDetails.
     */
    @RequestMapping(value = "/contactDetailss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContactDetails(@PathVariable Long id) {
        log.debug("REST request to delete ContactDetails : {}", id);
        contactDetailsRepository.delete(id);
        contactDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contactDetails", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contactDetailss/:query -> search for the contactDetails corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contactDetailss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ContactDetails> searchContactDetailss(@PathVariable String query) {
        return StreamSupport
            .stream(contactDetailsSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
