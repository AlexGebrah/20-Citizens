package telran.citizens.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.citizens.dao.Citizens;
import telran.citizens.dao.CitizensImpl;
import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CitizensTest {

    private Citizens citizens;
    private final Person person1 = new Person(1, "Alice", "Brown", LocalDate.of(1990, 5, 15));
    private final Person person2 = new Person(2, "Bob", "Smith", LocalDate.of(1985, 8, 22));
    private final Person person3 = new Person(3, "Charlie", "Brown", LocalDate.of(2000, 3, 10));
    List<Person> persons = List.of(person1, person2, person3);

    @BeforeEach
    void setUp() {
        citizens = new CitizensImpl(persons);
        citizens.add(person1);
        citizens.add(person2);
        citizens.add(person3);
    }

    @Test
    void testAdd () {
        Person newPerson = new Person(4, "David", "Johnson", LocalDate.of(1995, 1, 5));
        assertTrue(citizens.add(newPerson));
        assertFalse(citizens.add(person2));
        assertEquals(4, citizens.size());
    }

    @Test
    void testRemove () {
        assertTrue(citizens.remove(2));
        assertEquals(2, citizens.size());
        assertNull(citizens.find(2));
    }

    @Test
    void testFind () {
        assertEquals(person1, citizens.find(1));
        assertNull(citizens.find(99));
    }

    @Test
    void testFindAge () {
        List<Person> result = (List<Person>) citizens.find(30, 40);
        assertEquals(2, result.size());
    }

    @Test
    void testFindLastName () {
        List<Person> result = (List<Person>) citizens.find("Brown");
        assertEquals(2, result.size());
        assertTrue(result.contains(person1));
        assertTrue(result.contains(person3));
    }

    @Test
    void testGetAllPersonSortedById() {
        List<Person> result = (List<Person>) citizens.getAllPersonSortedById();
        assertEquals(List.of(person1, person2, person3), result);
    }

    @Test
    void testGetAllPersonSortedByAge() {
        List<Person> result = (List<Person>) citizens.getAllPersonSortedByAge();
        assertEquals(List.of(person2, person1, person3), result);
    }

    @Test
    void testGetAllPersonSortedByLastNAme() {
        List<Person> result = (List<Person>) citizens.getAllPersonSortedByLastNAme();
        assertEquals(List.of(person1, person3, person2), result);
    }

    @Test
    void testSize() {
        assertEquals(3, citizens.size());
        citizens.remove(2);
        assertEquals(2, citizens.size());
    }

}
