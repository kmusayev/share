package com.book.project.TestRun;

import com.libriary_management.books.Book;
import com.libriary_management.books.BookService;
import com.libriary_management.transactions.Transaction;
import com.libriary_management.transactions.TransactionService;
import com.libriary_management.users.User;
import com.libriary_management.users.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryTest {
    private final BookService bookService = new BookService();
    private final UserService userService = new UserService();
    private final User user = new User();
    private final TransactionService transactionService = new TransactionService();
    private final Scanner scanner = new Scanner(System.in);

    //Run main menu
    public void run() {
    	System.out.println("\n📚 LIBRARY MANAGEMENT SYSTEM");
        System.out.print("Enter your User ID to log in: ");
        String userId = scanner.nextLine();

        userService.findById(userId).ifPresentOrElse(user -> {
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                adminMenu(userId);
            } else {
                userMenu(userId);
            }
        }, () -> System.out.println("User not found."));
        while (true) {
            System.out.println("\n=== Please Enter valid user ID or see below available options===");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book by Title");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewBooks();
                case "2" -> searchBook();
                case "3" -> {
                    System.out.println("👋 Goodbye!");
                    run();
                    return;
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
 // Admin menu method
    private void adminMenu(String adminId) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book by Title");
            System.out.println("3. Add New Book");
            System.out.println("4. Add New User");
            System.out.println("5. Logout");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewBooks();
                case "2" -> searchBook();
                case "3" -> addBook();
                case "4" -> addUser();
                case "5" -> {
                    System.out.println("👋 Goodbye!");
                    run();
                    return;  // Log out and return to the main menu
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }
    //View all books menu
    private void viewBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            books.forEach(book -> System.out.printf("[%s] %s by %s (%d) - Available: %d%n",
                    book.getBookId(), book.getTitle(), book.getAuthor(),
                    book.getPublicationYear(), book.getAvailable()));
        }
    }
    
    //View assigned to user books
    private void viewUserBooks(String userId) {
    	
    	Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("❌ User not found.");
            return;
        }

        User user = userOpt.get();
        List<String> books = user.getIssuedBooks();

        if (books == null || books.isEmpty()) {
            System.out.println("📭 No books currently issued to user " + userId);
            return;
        }

        System.out.println("📚 Books issued to user " + userId + ":");
        for (String isbn : books) {
            bookService.findByISBN(isbn).ifPresentOrElse(
                book -> System.out.println(" - " + book.getTitle() + " (ISBN: " + isbn + ")"),
                () -> System.out.println(" - Unknown title (ISBN: " + isbn + ")")
            );
        }
    }
//Search for specific book based on title
    private void searchBook() {
        System.out.print("Enter title keyword: ");
        String keyword = scanner.nextLine();
        List<Book> results = bookService.searchByTitle(keyword);
        if (results.isEmpty()) {
            System.out.println("❌ No books found.");
        } else {
            results.forEach(book -> System.out.printf("%s by %s (Available: %d)%n",
                    book.getTitle(), book.getAuthor(), book.getAvailable()));
        }
    }
    
    //Generates ISBN number 
    public static class ISBNGenerator {
        private static final Random random = new Random();

        public static String generateISBN() {
            StringBuilder sb = new StringBuilder("978"); // Standard prefix for ISBN is 13
            for (int i = 0; i < 10; i++) {
                sb.append(random.nextInt(10)); // Add 10 more digits
            }
            return sb.toString();
        }
    }
    
    //Makes sure ISBN number is unique
    public static String generateUniqueISBN(List<Book> existingBooks) {
        String isbn;
        Set<String> existingISBNs = existingBooks.stream()
                .map(Book::getIsbn)
                .collect(Collectors.toSet());

        do {
            isbn = ISBNGenerator.generateISBN();
        } while (existingISBNs.contains(isbn));

        return isbn;
    }
    
    //Add a new book
    private void addBook() {
    	
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        String isbn=ISBNGenerator.generateISBN();
        System.out.print("Publication Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        String bookId = "B" + (bookService.getAllBooks().size() + 1001);
        Book newBook = new Book(bookId, title, author, isbn, year, quantity);
        bookService.addBook(newBook);
        System.out.println("Book added successfully! " + "- ISBN number is :" +isbn );
    }
 // Add a new user (Admin)
    private void addUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Library Card Number: ");
        String card = scanner.nextLine();
        System.out.print("Role (ADMIN or USER): ");
        String role = scanner.nextLine().toUpperCase();

        String userId = "U" + (userService.getAllUsers().size() + 2001);  // Auto-generate user ID
        User newUser = new User(userId, name, email, card, role, new ArrayList<>(), 0);
        userService.addUser(newUser);
        System.out.println("User added successfully with ID: " + userId);
    }
    
    //User menu
    private void userMenu(String userId) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View User Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Select option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewUserBooks(userId);
                case "2" -> {
                    System.out.print("Enter ISBN to issue: ");
                    String isbn = scanner.nextLine();
                    
                    bookService.findByISBN(isbn).ifPresentOrElse(book -> {
                    	
                        if (book.getAvailable() > 0) {
                        	if(user.getBookCount()>=3) {
                        		System.out.println("❌ Issued book count exceeds limit, you can not take more than 3 books, please return at least 1 book");
                        	}
                        	else {
                            user.setBookCount(user.getBookCount()+1);
                            transactionService.issueBook(userId, isbn);
                            book.setAvailable(book.getAvailable() - 1);
                            bookService.save();

                            System.out.println("✅ Book issued successfully.");
                            }
                        } else {
                            System.out.println("❌ Book not available.");
                        }
                    }, () -> System.out.println("❌ Book not found."));
                }
                case "3" -> {
                    System.out.print("Enter ISBN to return: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Mark book as LOST: Y/N ");
                    String lost = scanner.nextLine();
                    if(lost.equalsIgnoreCase("Y")) {
                         bookService.findByISBN(isbn).ifPresent(book -> {
                        	
                             book.setQuantity(book.getQuantity() -1);
                             book.setLost(book.getLost()+1);
                             bookService.save();
                             System.out.println("📕 Book marked as lost.");
                             System.out.println("📕 Please pay the full replacement cost of the book ($50).");
                         });
                         return;
                    }else 
                    {
                    
                    transactionService.returnBook(userId, isbn);
                    bookService.findByISBN(isbn).ifPresent(book -> {
                        book.setAvailable(book.getAvailable() + 1);
                        bookService.save();

                        System.out.println("📕 Book returned successfully.");
                    });
                    }
                }
                case "4" -> {
                    System.out.println("Logging out...");
                    run();
                    return;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }
    public static void main(String[] args) {
    	LibraryTest lib = new LibraryTest();
    	lib.run();

    	}
   
}