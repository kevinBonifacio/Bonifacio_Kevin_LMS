package com.lms;

public class Main {

    /**
     * purpose: This main method is not part of the actual App.
     * Its purpose is to call the main method App.java in order to initialize the application.
     * Reason: this is the only way I found to package the app into a jar using the maven shade plugin.
     */
    public static void main(String[] args) {
        App.main(args);
    }
}
