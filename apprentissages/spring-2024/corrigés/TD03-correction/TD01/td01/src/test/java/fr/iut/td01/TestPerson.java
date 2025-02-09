package fr.iut.td01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import fr.iut.td01.models.Person;

public class TestPerson {
    @Test
    public void testPerson(){
        Person p = new Person("toto",15, null);
        assertEquals("toto", p.getName());
        assertEquals(15, p.getAge());
        assertNull(p.getPhoto());        
    }
}
