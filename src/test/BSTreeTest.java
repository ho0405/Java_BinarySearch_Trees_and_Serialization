package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.TreeException;
import referenceBasedTreeImplementation.BSTree;
import utilities.Iterator;

class BSTreeTest {

    private BSTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new BSTree<>();
    }

    @Test
    void testBSTree() {
        assertNotNull(tree, "Tree should be initialized.");
    }

    @Test
    void testGetRoot() {
        try {
			assertNull(tree.getRoot(), "Root should be null for a new tree.");
		} catch (TreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    void testGetHeight() {
        assertEquals(-1, tree.getHeight(), "Height should be -1 for an empty tree.");
    }

    @Test
    void testSize() {
        assertEquals(0, tree.size(), "Size should be 0 for an empty tree.");
    }

    @Test
    void testIsEmpty() {
        assertTrue(tree.isEmpty(), "Tree should be empty initially.");
    }

    @Test
    void testClear() {
        tree.add(1);
        tree.clear();
        assertTrue(tree.isEmpty(), "Tree should be empty after clear.");
    }

    @Test
    void testContains() {
        tree.add(1);
        try {
			assertTrue(tree.contains(1), "Tree should contain the element after adding.");
		} catch (TreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    void testSearch() {
        tree.add(2);
        try {
			assertNotNull(tree.search(2), "Search should find the element that exists.");
		} catch (TreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    void testAdd() {
        assertTrue(tree.add(3), "Add should return true when a new element is added.");
    }

    @Test
    void testSearchElement() {
        tree.add(4);
        try {
			assertEquals(4, tree.searchElement(4), "SearchElement should return the element if it exists.");
		} catch (TreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    void testInorderIterator() {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        Iterator<Integer> iterator = tree.inorderIterator();
        assertTrue(iterator.hasNext(), "Iterator should have next on a non-empty tree.");
        assertEquals(1, iterator.next(), "Inorder traversal should return the smallest element first.");
    }

    @Test
    void testPreorderIterator() {
        // Similar to testInorderIterator, adjust for preorder traversal logic
    }

    @Test
    void testPostorderIterator() {
        // Similar to testInorderIterator, adjust for postorder traversal logic
    }


    @Test
    void testInorderTraversal() {
        tree.add(3);
        tree.add(1);
        tree.add(4);
        tree.add(2);
        List<Integer> traversal = tree.inorderTraversal();
        assertEquals(Arrays.asList(1, 2, 3, 4), traversal, "Inorder traversal should return elements in sorted order.");
    }
}

