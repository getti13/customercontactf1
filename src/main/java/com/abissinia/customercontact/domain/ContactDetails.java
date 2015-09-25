package com.abissinia.customercontact.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.abissinia.customercontact.domain.enumeration.PreferedContact;

import com.abissinia.customercontact.domain.enumeration.PreferedLanguage;

import com.abissinia.customercontact.domain.enumeration.Country;

/**
 * A ContactDetails.
 */
@Entity
@Table(name = "CONTACT_DETAILS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="contactdetails")
public class ContactDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "telephone")
    private Integer telephone;
    
    @Column(name = "sheba_miles")
    private Boolean shebaMiles;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "fax")
    private String fax;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "prefered_contact")
    private PreferedContact preferedContact;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "prefered_language")
    private PreferedLanguage preferedLanguage;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "zipcode")
    private Integer zipcode;
    
    @Column(name = "comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Boolean getShebaMiles() {
        return shebaMiles;
    }

    public void setShebaMiles(Boolean shebaMiles) {
        this.shebaMiles = shebaMiles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public PreferedContact getPreferedContact() {
        return preferedContact;
    }

    public void setPreferedContact(PreferedContact preferedContact) {
        this.preferedContact = preferedContact;
    }

    public PreferedLanguage getPreferedLanguage() {
        return preferedLanguage;
    }

    public void setPreferedLanguage(PreferedLanguage preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactDetails contactDetails = (ContactDetails) o;

        if ( ! Objects.equals(id, contactDetails.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContactDetails{" +
                "id=" + id +
                ", firstName='" + firstName + "'" +
                ", middleName='" + middleName + "'" +
                ", lastName='" + lastName + "'" +
                ", telephone='" + telephone + "'" +
                ", shebaMiles='" + shebaMiles + "'" +
                ", email='" + email + "'" +
                ", fax='" + fax + "'" +
                ", preferedContact='" + preferedContact + "'" +
                ", preferedLanguage='" + preferedLanguage + "'" +
                ", country='" + country + "'" +
                ", address='" + address + "'" +
                ", city='" + city + "'" +
                ", zipcode='" + zipcode + "'" +
                ", comment='" + comment + "'" +
                '}';
    }
}
