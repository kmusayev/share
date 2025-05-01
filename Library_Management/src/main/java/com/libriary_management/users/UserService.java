package com.libriary_management.users;


import com.fasterxml.jackson.core.type.TypeReference;

import managementServices.DataService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
	
	
    private final String userFilePath = System.getProperty("user.dir") + "/data/users.json";
    private List<User> users;

    public UserService() {
  
    	users = DataService.readList(userFilePath, new TypeReference<>() {});
	 
      
    }
    //update and save user details based on user id
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equalsIgnoreCase(updatedUser.getUserId())) {
                users.set(i, updatedUser);
                save(); 
                return;
            }
        }
    }
    
    //add a book ISBN to user profile, stop issuing already assigned book
    public void addIssuedBookToUser(String userId, String isbn) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserId().equalsIgnoreCase(userId)) {
                List<String> books = user.getIssuedBooks();
                if (books == null) {
                    books = new ArrayList<>();
                    user.setIssuedBooks(books);
                }

                if (!books.contains(isbn)) {
                    books.add(isbn);
                    user.setBookCount(books.size()); 
                    System.out.println("✅ Book added to user's issued list.");
                    
                }
                else {
                    System.out.println("⚠ User already has this book issued.");
                }

                users.set(i, user); 
                save();             
                return;
            }
        }
        System.out.println("❌ User not found.");
    }
    public void addUser(User user) {
        users.add(user);
        save();
    }

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> findById(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equalsIgnoreCase(userId))
                .findFirst();
    }

    public void save() {
    	DataService.writeList(userFilePath, users);
    }
}