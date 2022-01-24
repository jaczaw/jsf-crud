/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.okta.developer.application;

import com.okta.developer.entities.Book;
import com.okta.developer.entities.Address;
import com.okta.developer.entities.Customer;
import com.okta.developer.entities.Phone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

@Stateless
public class BookService {

    @PersistenceContext(unitName = "book-pu")
    private EntityManager entityManager;

    public void addBook(Book book)
    {
      entityManager.persist(book);
    }

    public void addCustomer(Customer customer)
    {
        entityManager.persist(customer);
    }

    public List<Book> getAllBooks()
    {
        CriteriaQuery<Book> cq = entityManager.getCriteriaBuilder().createQuery(Book.class);
        cq.select(cq.from(Book.class));
        return entityManager.createQuery(cq).getResultList();
    }

    public Book getBook(Integer bookId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        cq.select(book);
        cq.where(cb.equal(book.get("bookId"), bookId));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<Customer> getAllCustomers()
    {
        CriteriaQuery<Customer> cq = entityManager.getCriteriaBuilder().createQuery(Customer.class);
        cq.select(cq.from(Customer.class));
        return entityManager.createQuery(cq).getResultList();
    }
    public Customer getCustomer(Integer custId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> customer = cq.from(Customer.class);
        cq.select(customer);
        cq.where(cb.equal(customer.get("Id"), custId));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public void update(Book book) {
        entityManager.merge(book);
    }

    public void delete(Book book) {
        Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.bookId = :bookId");
        query.setParameter("bookId", book.getBookId());
        query.executeUpdate();
    }

    public Customer prepareCustomer(){

        Address address = new Address();
        Phone phone = new Phone();
        //phone.setId(1);
        phone.setNumber("608482648");
        //address.setId(1);
        address.setCity("Lublin");

        return Customer.builder()
                //.id(1)
                .firstName("Jan")
                .lastName("Nowak")
                .hasGoodCredit(true)
                .address(address)
                .phoneNumbers(Arrays.asList(phone))
                .build();
    }
}
