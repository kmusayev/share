package UnitTest;

import com.libriary_management.transactions.TransactionService;
import com.libriary_management.users.User;
import com.libriary_management.users.UserService;
import com.libriary_management.books.Book;
import com.libriary_management.books.BookService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private TransactionService transactionService;
    private UserService userService;
    private BookService bookService;

    @BeforeEach
    void setup() {
        transactionService = new TransactionService();
        userService = new UserService();
        bookService = new BookService();

        // Mock user
        User user = new User("U2004", "Khayyam2", "khayyam2@gmail.com", "LC1002", "USER", new ArrayList<>(), 0);
        userService.addUser(user);

        // Mock book
        Book book = new Book();
        book.setIsbn("TESTISBN");
        book.setTitle("Test Book");
        book.setAvailable(1);
        bookService.addBook(book);
    }

    @Test
    void testIssueBook() {
        transactionService.issueBook("U2004", "TESTISBN");

        User updatedUser = userService.findById("U2004").get();
        System.out.println(updatedUser.getIssuedBooks());
        assertTrue(updatedUser.getIssuedBooks().contains("TESTISBN"));
        
    }
}