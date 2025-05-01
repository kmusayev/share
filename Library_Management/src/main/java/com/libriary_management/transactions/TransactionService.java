package com.libriary_management.transactions;



import com.fasterxml.jackson.core.type.TypeReference;
import com.libriary_management.users.User;
import com.libriary_management.users.UserService;

import managementServices.DataService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
//json path file
public class TransactionService {
    private final String txFilePath = System.getProperty("user.dir") + "/data/transactions.json";
    private List<Transaction> transactions;

    //set default return date -  to 14 days
    public TransactionService() {
        transactions = DataService.readList(txFilePath, new TypeReference<>() {});
        for (Transaction tx : transactions) {
            if (tx.getDueDate() == null && tx.getIssueDate() != null) {
                tx.setDueDate(tx.getIssueDate().plusDays(14)); // Default return period
            }
        }

    
    }

   //assign book to user, change status to issued
    	public void issueBook(String userId, String isbn) {
    	    UserService userService = new UserService();
    	    
    	 
    	    Optional<User> userOpt = userService.findById(userId);
    	    if (userOpt.isEmpty()) {
    	        System.out.println("❌ User not found.");
    	        return;
    	    }

    	    // Issue the transaction
    	    String txId = "T" + UUID.randomUUID().toString().substring(0, 8);
    	    Transaction tx = new Transaction(txId, userId, isbn, LocalDate.now(), "ISSUED");
    	    transactions.add(tx);
    	    save(); // Save transaction

    	    
    	    userService.addIssuedBookToUser(userId, isbn);

    	    
    	}

    	//return book, increase the available book quantity in library, decrease book count assigned to user
    public void returnBook(String userId, String isbn) {
        User users =new User();
        UserService userService = new UserService();
        transactions.stream()
            .filter(tx -> tx.getUserId().equalsIgnoreCase(userId)
                    && tx.getisbn().equalsIgnoreCase(isbn)
                    && "ISSUED".equalsIgnoreCase(tx.getStatus()))
            .findFirst()
            .ifPresent(tx -> {
                tx.setReturnDate(LocalDate.now());
                tx.setStatus("RETURNED");
                
                if (LocalDate.now().isAfter(tx.getDueDate())) {
                    long daysLate = ChronoUnit.DAYS.between(tx.getDueDate(), LocalDate.now());
                    System.out.println("Book is returned " + daysLate + " day(s) late.");

                    // Optional: calculate a fine
                    int finePerDay = 2;
                    int totalFine = (int) daysLate * finePerDay;
                    System.out.println("Please pay a late fine of $" + totalFine);
                } else {
                    System.out.println("Book returned on time. Thank you!");
                }
                
                userService.findById(userId).ifPresent(user -> {
                    List<String> books = user.getIssuedBooks();
                    if (books != null && books.contains(isbn)) {
                        books.remove(isbn);
                        user.setBookCount(user.getBookCount()-1);
                        userService.save();
                        
                    }
                });
                save();
            });
        
    }

    public List<Transaction> getUserTransactions(String userId) {
        return transactions.stream()
                .filter(tx -> tx.getUserId().equalsIgnoreCase(userId))
                .collect(Collectors.toList());
    }

    public void save() {
    	DataService.writeList(txFilePath, transactions);
    }
}
