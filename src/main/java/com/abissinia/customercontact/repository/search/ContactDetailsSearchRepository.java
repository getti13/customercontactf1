package com.abissinia.customercontact.repository.search;

import com.abissinia.customercontact.domain.ContactDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ContactDetails entity.
 */
public interface ContactDetailsSearchRepository extends ElasticsearchRepository<ContactDetails, Long> {
}
