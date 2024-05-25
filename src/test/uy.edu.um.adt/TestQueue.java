package uy.edu.um.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.adt.queue.EmptyQueueException;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueue {
    private MyLinkedListImpl<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new MyLinkedListImpl<>();
    }

    @Test
    void testEnqueueAndSize() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    @Test
    void testDequeue() throws EmptyQueueException {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.dequeue());
        assertEquals(1, queue.dequeue());
    }

    @Test
    void testContains() throws EmptyQueueException {
        assertFalse(queue.contains(1));
        queue.enqueue(1);
        assertTrue(queue.contains(1));
        queue.dequeue();
        assertFalse(queue.contains(1));
    }

    @Test
    void testGet() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.get(0));
        assertEquals(2, queue.get(1));
    }

    @Test
    void testIsEmpty() throws EmptyQueueException {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }
}
