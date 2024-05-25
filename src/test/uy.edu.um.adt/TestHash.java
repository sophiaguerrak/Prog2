package uy.edu.um.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.hash.HashImpl;
import uy.edu.um.exceptions.InformacionInvalida;

import static org.junit.jupiter.api.Assertions.*;

public class TestHash {
    private HashImpl<String, Integer> hash;

    @BeforeEach
    void setUp() {
        hash = new HashImpl<>(10); // Un tamaño inicial arbitrario
    }

    @Test
    void testInsertAndSearch() throws InformacionInvalida {
        hash.insert("key1", 100);
        assertNotNull(hash.search("key1"));
        assertEquals(100, hash.search("key1"));
    }

    @Test
    void testDelete() throws InformacionInvalida {
        hash.insert("key2", 200);
        hash.delete("key2");
        assertNull(hash.search("key2"));
    }

    @Test
    void testContains() throws InformacionInvalida {
        hash.insert("key3", 300);
        assertTrue(hash.contains("key3"));
        hash.delete("key3");
        assertFalse(hash.contains("key3"));
    }

    @Test
    void testSize() throws InformacionInvalida {
        assertEquals(0, hash.size());
        hash.insert("key1", 100);
        hash.insert("key2", 200);
        assertEquals(2, hash.size());
        hash.delete("key1");
        assertEquals(1, hash.size());
    }


}
