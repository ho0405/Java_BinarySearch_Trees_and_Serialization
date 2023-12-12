package application;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WordNode implements Comparable<WordNode>, Serializable {
    private String word;
    private int frequency;
    private Set<Integer> lineNumbers;

    public WordNode(String word) {
        this.word = word;
        this.frequency = 1;
        this.lineNumbers = new HashSet<>();
    }

    public void incrementFrequency() {
        frequency++;
    }

    public void addLineNumber(int lineNumber) {
        lineNumbers.add(lineNumber);
    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    public Set<Integer> getLineNumbers() {
        return lineNumbers;
    }

    @Override
    public int compareTo(WordNode other) {
        return this.word.compareToIgnoreCase(other.word);
    }

    @Override
    public String toString() {
        return "Word: " + word + ", Frequency: " + frequency + ", Line Numbers: " + lineNumbers;
    }
}
