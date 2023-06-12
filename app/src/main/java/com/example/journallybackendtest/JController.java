package com.example.journallybackendtest;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Handles functionality of entire application.
 */
public final class JController {
    public static String userId = "-NVsAnh4G_RYk3B872I7";
    // Current User of application
    private static User currentUser;
    // Current Journal of application
    private static Journal currentJournal;
    // Current Entry of application
    private static Entry currentEntry;
    // Firebase Database variable
    private static CloudDatabase db;
    // Current activity in use
    private static JournallyActivity currentActivity;
    // Date format used
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    // Filepath of external storage files
    private static String filePath;
    private static ValueEventListener currentListener;

    /**
     * Class cannot be instantiated, meant to be statically accessed.
     */
    private JController() {

    }

    /**
     * Sets up the database and reads current user of application.
     */
    public static void setDatabase() {
        // New CloudDatabase
        db = new CloudDatabase();
        // Read data of currentUser
        /*db.readData(new CloudDatabase.JournallyCallback() {
            @Override
            public void onCallback(DataSnapshot snapshot) {
                setCurrentUser(snapshot.getValue(User.class));
                updateActivityContent();
            }
        }, db.getRoot().child("users").child(userId));*/
        changeCurrentUser("Hew Max", "Unitec123");
        // Set external file path used for audio files
        setAudioFilePath();
    }

    public static void changeCurrentUser(String name, String password) {
        removeOldListener();
        readUsers(name, password);
    }

    private static void readUser(String userId) {
        DatabaseReference userRef = db.getRoot().child("users").child(userId);
        System.out.println(userRef.toString());
        currentListener = db.readData(new CloudDatabase.JournallyCallback() {
            @Override
            public void onCallback(DataSnapshot snapshot) {
                setCurrentUser(snapshot.getValue(User.class));
                updateActivityContent();
            }
        }, userRef);
        userRef.addValueEventListener(currentListener);
    }

    private static void readUsers(String name, String password) {
        DatabaseReference usersRef = db.getReference("users");
        db.readDataOnce(new CloudDatabase.JournallyCallback() {
            @Override
            public void onCallback(DataSnapshot snapshot) {
                Iterable<DataSnapshot> userSnaps = snapshot.getChildren();
                for (DataSnapshot userS: userSnaps) {
                    User user = userS.getValue(User.class);
                    if (user.getPassword().equals(password) && user.getName().equals(name)) {
                        readUser(user.getId());
                        break;
                    }
                }
            }
        }, usersRef);
    }

    private static void removeOldListener() {
        if (currentUser != null) {
            DatabaseReference oldRef = db.getRoot().child("users").child(currentUser.getId());
            oldRef.removeEventListener(currentListener);
        }
    }

    /**
     * Insert User object into database
     * @param user
     */
    public static void insertUser(User user) {
        // Get users parent reference
        DatabaseReference users = db.getReference("users");
        // Get new ID
        String id = users.push().getKey();
        // Set new ID
        user.setId(id);
        // Insert User object into database at new ID key
        users.child(id).setValue(user);
    }

    /*
    Only user can be "inserted" into database.
    All other objects (Journal, Entry) are descendants of User.
    User is read from database, descendant Journals and Entries are updated within application (C.R.U.D).
    User, with updated descendants, is written to database.
     */

    /**
     * Update current Entry, including content of Entry.
     */
    public static void databaseUpdateCurrentContent() {
        // Get URL to currentEntry
        String url = "users/" + currentUser.getId() + "/listJournals/" + currentJournal.getId() + "/listEntries/" + getCurrentEntry().getId();
        // Get database reference using URL
        DatabaseReference contentRef = db.getReference(url);
        // Overwrite old Entry with updated Entry in database.
        db.insertData(contentRef, currentEntry);
    }

    /**
     * Update current Journal, including content of Journal.
     */
    public static void databaseUpdateCurrentJournal() {
        // Get URL to currentJournal
        String url = "users/" + currentUser.getId() + "/listJournals/" + currentJournal.getId();
        // Get database reference using URL
        DatabaseReference journalRef = db.getReference(url);
        // Overwrite old Journal with updated Journal in database.
        db.insertData(journalRef, currentJournal);
    }

    /**
     * Update current User, including content of User.
     */
    public static void databaseUpdateCurrentUser() {
        // Get URL to currentUser
        String url = "users/" + currentUser.getId();
        // Get database reference using URL
        DatabaseReference journalRef = db.getReference(url);
        // Overwrite old User with updated User in database.
        db.insertData(journalRef, currentUser);
    }

    /**
     * Update current activity of application.
     */
    public static void updateActivityContent() {
        currentActivity.updateContent();
    }

    // Local data methods

    /**
     * Create and return new User object using passed String name.
     * @param name
     * @return
     */
    public static User createUser(String name, String password) {
        // Get unique ID from database
        String id = db.getNewId();
        // Create and return
        User newUser = new User(id, name, password);
        return newUser;
    }

    private static DatabaseReference getUserReference(String userId) {
        return db.getRoot().child("users").child(userId);
    }

    /**
     * Create and return new Journal object using passed Strings title and description.
     * @param title
     * @param description
     * @return
     */
    public static Journal createJournal(String title, String description) {
        // Get unique ID from database
        String id = db.getNewId();
        // Get current date of creation
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String creationDate = dtf.format(LocalDateTime.now());
        // Create and return
        Journal newJournal = new Journal(id, title, description, creationDate);
        return newJournal;
    }

    /**
     * Returns Database reference of passed Journal argument, requires parent User of Journal argument.
     * @param user
     * @param journal
     * @return
     */
    private static DatabaseReference getJournalReference(User user, Journal journal) {
        return db.getRoot().child("users").child(user.getId()).child("listJournals").child(journal.getId());
    }

    /**
     * Creates and returns new Entry using passed String title.
     * @param title
     * @return
     */
    public static Entry createEntry(String title) {
        // Get unique ID from database
        String id = db.getNewId();
        // Get current date of creation
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String creationDate = dtf.format(LocalDateTime.now());
        // Create and return
        Entry newEntry = new Entry(id, title, creationDate);
        // Create and insert default content
        //newEntry.insertContent(new Content(""), 0);
        return newEntry;
    }

    /**
     * Returns Database reference of passed Entry argument, requires parent User and parent Journal argument.
     * @param user
     * @param journal
     * @param entry
     * @return
     */
    private static DatabaseReference getEntryReference(User user, Journal journal, Entry entry) {
        return db.getRoot().child("users").child(user.getId()).child("listJournals").child(journal.getId()).child("listEntries").child(entry.getId());
    }

    /**
     * Creates and returns new Content object with passed String content.
     * @param content
     * @return
     */
    public static Content createContent(String content) {
        Content newContent = new Content(content);
        return newContent;
    }

    /**
     * Sets file path to external audio files.
     */
    private static void setAudioFilePath() {
        // Static instance of application context.
        Context context = MainActivity.getContext();
        filePath = context.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * Returns AudioFilePath.
     * @return
     */
    public static String getAudioFilePath() {
        return filePath;
    }

    // Getters + Setters

    /**
     * Returns currentActivity.
     * @return
     */
    public static JournallyActivity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Sets currentActivity.
     * @param activity
     */
    public static void setCurrentActivity(JournallyActivity activity) { currentActivity = activity; }

    /**
     * Returns currentUser.
     * @return
     */
    public static User getCurrentUser() { return currentUser; }

    /**
     * Sets currentUser.
     * @param currentUser
     */
    public static void setCurrentUser(User currentUser) { JController.currentUser = currentUser; }

    /**
     * Returns currentJournal.
     * @return
     */
    public static Journal getCurrentJournal() { return currentJournal; }

    /**
     * Sets currentJournal.
     * @param currentJournal
     */
    public static void setCurrentJournal(Journal currentJournal) {
        JController.currentJournal = currentJournal;
    }

    /**
     * Returns currentEntry.
     * @return
     */
    public static Entry getCurrentEntry() { return currentEntry; }

    /**
     * Sets currentEntry.
     * @param currentEntry
     */
    public static void setCurrentEntry(Entry currentEntry) {
        JController.currentEntry = currentEntry;
    }
}
