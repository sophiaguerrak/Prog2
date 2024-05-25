package uy.edu.um.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.edu.um.adt.ntree.Tree;
import uy.edu.um.adt.ntree.TreeImpl;

import static org.junit.jupiter.api.Assertions.*;

public class TestTree {
    private TreeImpl<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new TreeImpl<>(10);
    }

    @Test
    void testAddAndSearchValue() {
        tree.add(15, 10);
        assertNotNull(tree.searchValue(15));
    }

    @Test
    void testGetValueAndSetValue() {
        assertEquals(10, tree.getValue());
        tree.setValue(20);
        assertEquals(20, tree.getValue());
    }

    @Test
    void testGetChilds() {
        tree.add(15, 10);
        tree.add(20, 10);
        Tree<Integer>[] childs = tree.getChilds();
        assertEquals(2, childs.length);
        assertEquals(15, childs[0].getValue());
        assertEquals(20, childs[1].getValue());
    }

    @Test
    void testSearchValueNotFound() {
        assertNull(tree.searchValue(15));
        tree.add(15, 10);
        assertNotNull(tree.searchValue(15));
    }
}
