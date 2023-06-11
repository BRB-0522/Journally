package com.example.journallybackendtest;

/**
 * Represents a Content of the application.
 * Represented in Database.
 */
public class Content {
    // Actual content string of Content object
    String content;

    /**
     * Empty constructor used by Firebase Database methods to instantiate a new Content Object from a database read.
     */
    public Content() {

    }

    /**
     * Instantiate a new Content object with passed content.
     * @param content
     */
    public Content(String content) {
        this.content = content;
    }

    // Getters and Setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
