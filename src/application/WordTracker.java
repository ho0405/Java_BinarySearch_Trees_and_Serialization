package application;

import referenceBasedTreeImplementation.BSTree;
import java.io.*;
import java.util.*;

import exceptions.TreeException;

public class WordTracker {
    private BSTree<WordNode> wordTree;
    private String inputFile;
    private String outputFile;
    private String option;

    public WordTracker(String[] args) {
        // Initialize the tree
        wordTree = new BSTree<>();

        // Check and parse arguments
        if (args.length < 2) {
            System.out.println("Usage: java WordTracker <input.txt> -pf/-pl/-po [-f <output.txt>]");
            System.exit(1);
        }

        inputFile = args[0];
        option = args[1];

        if (args.length == 4 && args[2].equals("-f")) {
            outputFile = args[3];
        }

        // Deserialize the tree if repository exists
        File repoFile = new File("repository.ser");
        if (repoFile.exists()) {
            deserializeTree();
        }

        processFile(inputFile);
        saveTree();
        printWords(option);
    }

    private void deserializeTree() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("repository.ser"))) {
            wordTree = (BSTree<WordNode>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading the tree: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveTree() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("repository.ser"))) {
            oos.writeObject(wordTree);
        } catch (IOException e) {
            System.err.println("Error saving the tree: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;
                String[] words = line.split("\\W+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        word = word.toLowerCase();
                        WordNode wordNode = new WordNode(word);
                        try {
                            if (wordTree.contains(wordNode)) {
                                wordNode = wordTree.searchElement(wordNode);
                                wordNode.incrementFrequency();
                                wordNode.addLineNumber(lineNumber);
                            } else {
                                wordNode.addLineNumber(lineNumber);
                                wordTree.add(wordNode);
                            }
                        } catch (TreeException e) {
                            System.err.println("Error processing word '" + word + "': " + e.getMessage());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            e.printStackTrace();
        }
    }

    private void printWords(String mode) {
        PrintStream output = null;
        try {
            // Open the output stream to the file if the outputFile is not null
            if (outputFile != null) {
                output = new PrintStream(new File(outputFile));
            } else {
                // Otherwise, set output to System.out
                output = System.out;
            }

            // Print the words using the output stream
            for (WordNode node : wordTree.inorderTraversal()) {
                if (mode.equals("-pf")) {
                    output.println(node.getWord() + ": " + node.getFrequency());
                } else if (mode.equals("-pl")) {
                    output.println(node.getWord() + ": " + node.getLineNumbers());
                } else if (mode.equals("-po")) {
                    output.println(node);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // If the output stream was a file, close it
            if (output != null && outputFile != null) {
                output.close();
            }
        }
    }


    public static void main(String[] args) {
        new WordTracker(args);
    }
}

