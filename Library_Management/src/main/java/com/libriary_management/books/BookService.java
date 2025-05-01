package com.libriary_management.books;

import com.fasterxml.jackson.core.type.TypeReference;

import managementServices.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {
    private final String bookFilePath = System.getProperty("user.dir") + "/data/books.json";
    private List<Book> books;

    public BookService() {
        books = DataService.readList(bookFilePath, new TypeReference<>() {});
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        save();
    }

    public Optional<Book> findByISBN(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .findFirst();
    }

    public List<Book> searchByTitle(String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void save() {
    	DataService.writeList(bookFilePath, books);
    }
}
