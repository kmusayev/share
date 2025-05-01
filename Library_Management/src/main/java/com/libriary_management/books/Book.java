package com.libriary_management.books;

import java.util.Random;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private int quantity;
    private int available;
    private int lost;

    
    public Book() {
    	
    }
    
    public Book(String bookId, String title, String author, String isbn, int publicationYear, int quantity) {
        this.setBookId(bookId);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.setPublicationYear(publicationYear);
        this.quantity = quantity;
        this.setAvailable(quantity);
        this.lost=lost;
    }
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	
	public int getAvailable() {
		return available;
	}

	
	public void setAvailable(int available) {
		this.available = available;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}
    
}