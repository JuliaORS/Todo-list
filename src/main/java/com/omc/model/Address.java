package com.omc.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipcode;
    private String country;

    public Address(String street, String city, String zipcode, String country) {
        setStreet(street);
        setCity(city);
        setZipcode(zipcode);
        setCountry(country);
    }
    public Address(){}
}
