package application;

import java.io.IOException;

/**
 *  The Driver class contains the main method to initialize the WordTracker
 */
public class AppDriver {

    /**
     * The main method of the program.
     *
     * @param args The command-line arguments passed to the program.
     * @throws IOException 
     */
    public static void main(String[] args) {
        new WordTracker(args);
    }
}
