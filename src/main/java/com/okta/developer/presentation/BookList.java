package com.okta.developer.presentation;

import com.okta.developer.application.BookService;
import com.okta.developer.entities.Book;
import com.okta.developer.entities.Customer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class BookList {

    @Inject
    private BookService bookService;
    private List<Book> booksAvailable;
    private List<Customer> customers;

    @PostConstruct
    public void postConstruct() {
        booksAvailable = bookService.getAllBooks();
        customers = bookService.getAllCustomers();
    }

    public List<Book> getBooksAvailable() {
        return booksAvailable;
    }
    public List<Customer> getCustomers() {
        return customers;
    }
}