package com.example.journallybackendtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User of the application.
 * Represented in Database.
 */
public class User {
    // Unique ID
    private String id;
    // Discern first name and last name by parsing the space in between the two sub-strings as a delimiter
    private String name;
    private String password;
    // List of Journal Objects
    private List<Journal> listJournals;

    /**
     * Empty constructor used by Firebase Database methods to instantiate a new User Object from a database read.
     */
    public User() {

    }

    /**
     * Instantiate a new User object with passed id and name.
     * @param id
     * @param name
     */
    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        listJournals = new ArrayList<>();
    }

    /**
     * Get id of User.
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Set id of User.
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get name of User.
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of User.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    /**
     * Get list of journals of User.
     * @return List<Journal> listJournals
     */
    public List<Journal> getListJournals() {
        return listJournals;
    }

    /**
     * Set list of journals of User.
     * @param listJournals
     */
    public void setListJournals(List<Journal> listJournals) {
        this.listJournals = listJournals;
    }

    /**
     * Get a specific journal of User.
     * @return Journal OR null
     */
    public Journal getJournal(String id) {
        for (Journal j: listJournals) {
            if (j.getId().equals(id)) {
                return j;
            }
        }
        return null;
    }

    /**
     * Add a new journal to the journal list of User.
     * @param journal
     */
    public void addJournal(Journal journal) {
        listJournals.add(journal);
    }
}
