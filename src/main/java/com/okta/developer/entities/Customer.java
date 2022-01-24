package com.okta.developer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Customer implements java.io.Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private boolean hasGoodCredit;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "address_ID")
    private Address address;

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="CUSTOMER_ID")
    private Collection<Phone> phoneNumbers = new ArrayList<>();


//
//    @OneToOne(cascade={CascadeType.ALL})
//    private CreditCard creditCard;
//
//    @ManyToMany(mappedBy="customers")
//    private Collection<Reservation> reservations = new ArrayList<Reservation>();

}