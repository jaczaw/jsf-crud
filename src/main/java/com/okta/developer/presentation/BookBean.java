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
package com.okta.developer.presentation;

import com.okta.developer.application.BookService;
import com.okta.developer.entities.Book;
import com.okta.developer.entities.Customer;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class BookBean {

    @Inject
    private BookService bookService;
    private List<Book> booksAvailable;
    private String bookTitle;
    private Integer bookId;
    private Book book;
    private Customer customer;
    private String customerName;
    private static final String ACTION_SUCCESS = "success";

    @PostConstruct
    public void postConstruct() {
        String bookIdParam = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("bookId");
        if (bookIdParam != null) {
            bookId = Integer.parseInt(bookIdParam);
            book = bookService.getBook(bookId);
            customer = bookService.getAllCustomers().get(0);
            bookTitle = book.getBookTitle();
        }
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public List<Book> getBooksAvailable() {
        return booksAvailable;
    }

    public void setBooksAvailable(List<Book> booksAvailable) {
        this.booksAvailable = booksAvailable;
    }

    public String fetchBooks()
    {
        booksAvailable=bookService.getAllBooks();
        return ACTION_SUCCESS;
    }

    public String add()
    {
        Book book = new Book();
        book.setBookTitle(bookTitle);
        bookService.addBook(book);
        bookService.addCustomer(bookService.prepareCustomer());
        return ACTION_SUCCESS;
    }

    public String update() {
        book.setBookTitle(bookTitle);
        bookService.update(book);
        return ACTION_SUCCESS;
    }

    public String delete() {
        bookService.delete(book);
        return ACTION_SUCCESS;
    }

    public String getCustomerName(){
        StringBuilder name = new StringBuilder();
        name.append(this.customer.getFirstName());
        name.append(" ");
        name.append(this.customer.getLastName());
        name.append(" ");
        name.append(this.customer.getAddress().getCity());
        name.append(" ");
        name.append(this.customer.getPhoneNumbers().stream().findFirst().get().getNumber());
        return name.toString();
    }
}
