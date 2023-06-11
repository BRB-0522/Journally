package com.example.journallybackendtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Abstract class used to enforce an interface for all Activity Classed that the JController can interact with.
 */
public abstract class JournallyActivity extends AppCompatActivity {
    protected String tag;

    /**
     * Create the UI of the Activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Update the content of the Activity according to individual implementation.
     * Used by JController upon successful data read from database.
     */
    abstract void updateContent();
}
