package com.example.journallybackendtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.List;

public class Content_view extends JournallyActivity {


    private LinearLayout layout;
    private View recorder;
    private AudioEntryRecorder entryRecorder;
    private View playback;
    private AudioEntryPlayback entryPlayback;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);

        tag = this.getClass().getName();
    }


    public void updateContent() {
        user = JController.getCurrentUser();
        if (user == null) {
            Log.v(tag, "User does not exist; null");
            return;
        }
        JController.setCurrentJournal(user.getJournal("0"));
        JController.setCurrentEntry(JController.getCurrentJournal().getEntry("0"));
        //txtUserName.setText(user.getName());
        List<Content> ourContent = JController.getCurrentEntry().getListContent();

        clearAudioView();
        if (JController.getCurrentEntry().getAudioId() == null) {
            createRecorder();
        } else {
            createPlayback();
        }

        //updateRecyclerView(ourContent);
    }

    private void createRecorder() {
        clearAudioView();
        recorder = getLayoutInflater().inflate(R.layout.audio_recorder, null);
        //layout.addView(recorder);
        entryRecorder = new AudioEntryRecorder(recorder, JController.getCurrentEntry());
        entryRecorder.setButtonEvents(new AudioEntryRecorder.eventsInterface() {
            @Override
            public void setRecordOn() {

            }

            @Override
            public void setRecordOff() {
                createPlayback();
            }
        });
    }

    private void createPlayback() {
        clearAudioView();
        playback = getLayoutInflater().inflate(R.layout.audio_playback, null);
        layout.addView(playback);
        entryPlayback = new AudioEntryPlayback(playback, JController.getCurrentEntry());
        entryPlayback.setButtonEvents(new AudioEntryPlayback.eventsInterface() {
            @Override
            public void setPlayOn() {

            }

            @Override
            public void setPlayOff() {

            }

            @Override
            public void deleteAudio() {
                JController.getCurrentEntry().setAudioId(null);
                String audioSavePath = JController.getAudioFilePath() + "/" + JController.getCurrentEntry().getAudioId();
                File file = new File(audioSavePath);
                if(file.delete()) {
                    System.out.println("File has been deleted at: " + audioSavePath);
                }
                createRecorder();
            }

        });
    }

    private void clearAudioView() {
        if (recorder != null) {
            layout.removeView(recorder);
            recorder = null;
            entryRecorder = null;

        } else if (playback != null) {
            layout.removeView(playback);
            playback = null;
            entryPlayback = null;
        }
    }
}