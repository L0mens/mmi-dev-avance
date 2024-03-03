package fr.iut.td01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.iut.td01.models.UserData;

public class TestUser {
    @Test
    public void testCreateUser(){
        UserData user = new UserData("user","toto", false);
        assertEquals("user", user.getLogin());
        assertEquals("toto", user.getPassword());
        assertFalse(user.isAdmin());
    }

    @Test
    public void testCreateAdmin(){
        UserData user = new UserData("admin","5trongPa55", true);
        assertEquals("admin", user.getLogin());
        assertEquals("5trongPa55", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @Test
    public void testCreateDefault(){
        UserData user = new UserData();
        assertFalse(user.isAdmin());
    }
}
