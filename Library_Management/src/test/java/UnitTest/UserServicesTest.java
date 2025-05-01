package UnitTest;

import com.libriary_management.users.User;
import com.libriary_management.users.UserService;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testAddAndFindUser() {
        User user = new User();
        user.setUserId("U9001");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setIssuedBooks(new ArrayList<>());

        userService.addUser(user);

        Optional<User> retrieved = userService.findById("U9001");
        assertTrue(retrieved.isPresent());
        assertEquals("Test User", retrieved.get().getName());
    }
}