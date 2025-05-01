package UnitTest;

import com.libriary_management.books.Book;
import com.libriary_management.books.BookService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    void init() {
        bookService = new BookService();
    }

    @Test
    void testAddAndFindBook() {
        Book book = new Book();
        book.setIsbn("ISBN123");
        book.setTitle("JUnit for Beginners");

        bookService.addBook(book);

        assertTrue(bookService.findByISBN("ISBN123").isPresent());
    }
}