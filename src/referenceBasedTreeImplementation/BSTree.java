package referenceBasedTreeImplementation;

import utilities.BSTreeADT;
import utilities.Iterator;
import exceptions.TreeException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

@SuppressWarnings("serial")
public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable {
    private static final long serialVersionUID = -6339417738455099688L;
	private BSTreeNode<E> root;

    // Constructor
    public BSTree() {
        root = null;
    }

    // Method to get the root of the tree
    @Override
    public BSTreeNode<E> getRoot() throws TreeException {
        if (root == null) {
            throw new TreeException("The tree is empty.");
        }
        return root;
    }

    // Method to get the height of the tree
    @Override
    public int getHeight() {
        return getHeightRec(root);
    }

    // Recursive helper method for getHeight
    private int getHeightRec(BSTreeNode<E> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(getHeightRec(node.getLeft()), getHeightRec(node.getRight()));
    }

    // Method to get the size of the tree
    @Override
    public int size() {
        return sizeRec(root);
    }

    // Recursive helper method for size
    private int sizeRec(BSTreeNode<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeRec(node.getLeft()) + sizeRec(node.getRight());
    }

    // Method to check if the tree is empty
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    // Method to clear the tree
    @Override
    public void clear() {
        root = null;
    }

    // Method to check if the tree contains a given entry
    @Override
    public boolean contains(E entry) throws TreeException {
        return search(entry) != null;
    }

    // Method to search for a node with a given entry
    @Override
    public BSTreeNode<E> search(E entry) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("The tree is empty.");
        }
        return searchRec(root, entry);
    }

    // Recursive helper method for search
    private BSTreeNode<E> searchRec(BSTreeNode<E> node, E entry) {
        if (node == null) {
            return null;
        }
        int comparison = entry.compareTo(node.getElement());
        if (comparison == 0) {
            return node;
        } else if (comparison < 0) {
            return searchRec(node.getLeft(), entry);
        } else {
            return searchRec(node.getRight(), entry);
        }
    }

    // Method to add a new entry to the tree
    @Override
    public boolean add(E newEntry) {
        if (newEntry == null) {
            throw new NullPointerException("Cannot add null value to the tree");
        }
        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            return true;
        } else {
            return addRec(root, newEntry);
        }
    }

    private boolean addRec(BSTreeNode<E> node, E entry) {
        int comparison = entry.compareTo(node.getElement());
        if (comparison == 0) {
            // Duplicate value, not adding. (Consider updating occurrences here)
            return false;
        } else if (comparison < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTreeNode<>(entry));
                return true;
            } else {
                return addRec(node.getLeft(), entry);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new BSTreeNode<>(entry));
                return true;
            } else {
                return addRec(node.getRight(), entry);
            }
        }
    }

 // In your BSTree class
    public E searchElement(E entry) throws TreeException {
        BSTreeNode<E> node = search(entry);
        if (node != null) {
            return node.getElement();
        }
        return null;
    }

    @Override
    public Iterator<E> inorderIterator() {
        return new InorderIterator<>(root);
    }

    @Override
    public Iterator<E> preorderIterator() {
        return new PreorderIterator<>(root);
    }

    @Override
    public Iterator<E> postorderIterator() {
        return new PostorderIterator<>(root);
    }

    // Nested InorderIterator class
    private class InorderIterator<T extends Comparable<? super T>> implements Iterator<T> {
        private Stack<BSTreeNode<T>> stack = new Stack<>();

        public InorderIterator(BSTreeNode<T> root) {
            pushLeft(root);
        }

        private void pushLeft(BSTreeNode<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BSTreeNode<T> node = stack.pop();
            T result = node.getElement();
            pushLeft(node.getRight());
            return result;
        }
    }

    // Nested PreorderIterator class
    private class PreorderIterator<T extends Comparable<? super T>> implements Iterator<T> {
        private Stack<BSTreeNode<T>> stack = new Stack<>();

        public PreorderIterator(BSTreeNode<T> root) {
            if (root != null) {
                stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BSTreeNode<T> node = stack.pop();
            T result = node.getElement();
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
            return result;
        }
    }

    // Nested PostorderIterator class
    private class PostorderIterator<T extends Comparable<? super T>> implements Iterator<T> {
        private Stack<BSTreeNode<T>> stack1 = new Stack<>();
        private Stack<BSTreeNode<T>> stack2 = new Stack<>();

        public PostorderIterator(BSTreeNode<T> root) {
            if (root != null) {
                stack1.push(root);
            }
            while (!stack1.isEmpty()) {
                BSTreeNode<T> node = stack1.pop();
                stack2.push(node);
                if (node.getLeft() != null) {
                    stack1.push(node.getLeft());
                }
                if (node.getRight() != null) {
                    stack1.push(node.getRight());
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack2.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return stack2.pop().getElement();
        }
    }

	

	public List<E> inorderTraversal() {
        List<E> resultList = new ArrayList<>();
        inorderTraversalRec(root, resultList);
        return resultList;
    }

    private void inorderTraversalRec(BSTreeNode<E> node, List<E> resultList) {
        if (node != null) {
            inorderTraversalRec(node.getLeft(), resultList);
            resultList.add(node.getElement());
            inorderTraversalRec(node.getRight(), resultList);
        }
    }


}
