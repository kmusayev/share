package com.libriary_management.users;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userId;
    private String name;
    private String email;
    private String libraryCardNumber;
    private String role;
    private List<String> issuedBooks;
    private int bookCount;

    public User() {
    	 this.issuedBooks = new ArrayList<>();
    }

    public User(String userId, String name, String email, String libraryCardNumber, String role, ArrayList<String> arrayList, int bookCount) {
        this.setUserId(userId);
        this.name = name;
        this.email = email;
        this.setLibraryCardNumber(libraryCardNumber);
        this.setRole(role);
        this.issuedBooks = issuedBooks != null ? issuedBooks : new ArrayList<>();
        this.bookCount=bookCount;
    }
	    
	   
	    public String getName() {
	        return name;  
	    }

	    
	    public void setName(String name) {
	        this.name = name; 
	    }
	    
	    
	
	    public String getEmail() {
	        return email;  
	    }

	  
	    public void setEmail(String email) {
	        this.email = email;  
	    }

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getLibraryCardNumber() {
			return libraryCardNumber;
		}

		public void setLibraryCardNumber(String libraryCardNumber) {
			this.libraryCardNumber = libraryCardNumber;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public List<String> getIssuedBooks() {
			return issuedBooks;
		}

		public void setIssuedBooks(List<String> issuedBooks) {
			 this.issuedBooks = issuedBooks != null ? issuedBooks : new ArrayList<>();
		}

		public int getBookCount() {
			return bookCount;
		}

		public void setBookCount(int bookCount) {
			this.bookCount = bookCount;
		}

		
	 
}
