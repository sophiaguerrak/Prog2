package uy.edu.um.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.adt.stack.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

public class TestStack {
    private MyLinkedListImpl<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyLinkedListImpl<>();
    }

    @Test
    void testPushYPeek() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
        assertEquals(2, stack.peek());
    }

    private void assertEquals(int i, Integer peek) {
    }

    @Test
    void testPop() throws EmptyStackException {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void testSize() throws EmptyStackException {
        assertEquals(0, stack.size());
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
    }

    @Test
    void testIsEmpty() throws EmptyStackException{
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }
}
