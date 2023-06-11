package com.example.journallybackendtest;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readByte;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Handles database functionality with associated Firebase cloud database
 */
public class CloudDatabase {
    // Database variable
    private FirebaseDatabase database;
    // Firebase Storage variable
    private FirebaseStorage storage;
    // Root reference of database
    private DatabaseReference root;

    /**
     * Create CloudDatabase object.
     * Set up database and root variables
     */
    public CloudDatabase() {
        // Get instance of database from link
        database = FirebaseDatabase.getInstance("https://journallybackendtest-default-rtdb.firebaseio.com/");
        // Set root reference variable
        root = database.getReference();
    }

    /**
     * Insert data value of any generic type at passed database reference.
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void insertData(DatabaseReference key, T value) {
        key.setValue(value);
    }

    /**
     * Insert audio file into storage
     * @param audioName
     */
    public void insertAudio(String audioName) {
        StorageReference audioRef = storage.getReference("audio").child(audioName);
        String path = MainActivity.getContext().getExternalFilesDir(null).getAbsolutePath() + "/" + audioName; // Audio File path
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        byte[] arr = new byte[0];
        try {
            arr = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        audioRef.putBytes(arr);

    }

    /**
     * Returns DatabaseReference variable from String url.
     * @param ref
     * @return
     */
    public DatabaseReference getReference(String ref) {
        return database.getReference(ref);
    }

    /**
     * Returns root DatabaseReference variable of database.
     * @return
     */
    public DatabaseReference getRoot() {
        return root;
    }

    /**
     * Interface used to execute external code upon reading data of database.
     */
    public interface JournallyCallback {
        void onCallback(DataSnapshot snapshot);
    }

    /**
     * Reads data at passed reference.
     * Continues reading if data is altered.
     * Executes external code through JournallyCallback interface.
     * @param jCallback
     * @param reference
     * @return ValueEventListener instance
     */
    public ValueEventListener readData(JournallyCallback jCallback, DatabaseReference reference) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jCallback.onCallback(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        };
        return listener;
    }

    /**
     * Reads data at passed reference once.
     * Executes external code through JournallyCallback interface.
     * @param jCallback
     * @param reference
     */
    public void readDataOnce(JournallyCallback jCallback, DatabaseReference reference) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jCallback.onCallback(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    /**
     * Returns a new valid ID sources from Firebase Database functionality.
     * @return
     */
    public String getNewId() {
        return root.push().getKey();
    }
}
