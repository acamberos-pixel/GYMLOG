package com.example.gym3;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.gym3.database.entities.User;

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testUserPassword() {
        User user = new User("admin1", "admin1");
        assertEquals("admin1", user.getPassword());
        assertEquals("admin1", user.getUsername());
    }

    @Test
    public void testUserIsNotAdminByDefault() {
        User user = new User("testuser1", "testuser1");
        assertFalse(user.isAdmin());
    }

    @Test
    public void testUserAdminFlag() {
        User user = new User("admin1", "admin1");
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }
}