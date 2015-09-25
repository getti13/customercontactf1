package com.abissinia.customercontact.repository;

import com.abissinia.customercontact.domain.ContactDetails;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContactDetails entity.
 */
public interface ContactDetailsRepository extends JpaRepository<ContactDetails,Long> {

}
