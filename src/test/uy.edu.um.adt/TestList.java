package uy.edu.um.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.adt.linkedlist.MyList;

import static org.junit.jupiter.api.Assertions.*;

class TestList {
    private MyList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedListImpl<>();
    }

    @Test
    void testAddAndGet() {
        list.add(1);
        list.add(2);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void testContains() {
        assertFalse(list.contains(1));
        list.add(1);
        assertTrue(list.contains(1));
    }

    @Test
    void testRemove() {
        list.add(1);
        list.add(2);
        assertTrue(list.contains(2));
        list.remove(2);
        assertFalse(list.contains(2));
    }

    @Test
    void testSize() {
        assertEquals(0, list.size());
        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
        list.remove(1);
        assertEquals(1, list.size());
    }

    @Test
    void testGetFirst() {
        assertNull(list.getFirst());
        list.add(1);
        assertNotNull(list.getFirst());
        assertEquals(1, list.getFirst().getValue());
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add(1);
        assertFalse(list.isEmpty());
    }

}