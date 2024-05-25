package uy.edu.um.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.binarytree.SearchBinaryTreeImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinaryTree {
    private SearchBinaryTreeImpl<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new SearchBinaryTreeImpl<>();
    }

    @Test
    void testAddAndContains() {
        assertFalse(tree.contains(10));
        tree.add(10);
        assertTrue(tree.contains(10));
    }

    @Test
    void testRemove() {
        tree.add(10);
        tree.add(5);
        assertTrue(tree.contains(5));
        tree.remove(5);
        assertFalse(tree.contains(5));
    }

    @Test
    void testFind() {
        tree.add(10);
        assertEquals(10, tree.find(10));
        assertNull(tree.find(5));
    }

    @Test
    void testPreOrder() {
        tree.add(10);
        tree.add(5);
        tree.add(15);
        List<Integer> preOrder = tree.preOrder();
        assertEquals(List.of(10, 5, 15), preOrder);
    }

    @Test
    void testInOrder() {
        tree.add(10);
        tree.add(5);
        tree.add(15);
        List<Integer> inOrder = tree.inOrder();
        assertEquals(List.of(5, 10, 15), inOrder);
    }

    @Test
    void testPosOrder() {
        tree.add(10);
        tree.add(5);
        tree.add(15);
        List<Integer> posOrder = tree.posOrder();
        assertEquals(List.of(5, 15, 10), posOrder);
    }
}
