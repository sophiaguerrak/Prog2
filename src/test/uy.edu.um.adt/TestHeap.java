package uy.edu.um.adt;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.heap.MyHeapImpl;

public class TestHeap {
    private MyHeapImpl<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeapImpl<>(10);
    }

    @Test
    void testInsertAndSize() {
        assertEquals(0, heap.getSize());
        heap.insert(10);
        assertEquals(1, heap.getSize());
        heap.insert(20);
        assertEquals(2, heap.getSize());
        assertEquals(Integer.valueOf(20), heap.get());
    }

    @Test
    void testGet() {
        heap.insert(15);
        heap.insert(10);
        heap.insert(30);
        heap.insert(20);
        assertEquals(Integer.valueOf(30), heap.get());
        assertEquals(3, heap.getSize());
        assertEquals(Integer.valueOf(20), heap.get());
    }

    @Test
    void testEmptyHeap() {
        assertNull(heap.get());
    }

}

