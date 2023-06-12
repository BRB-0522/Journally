package com.example.journallybackendtest;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.List;
import java.util.Random;

public class MainActivity extends JournallyActivity {
    private RecyclerView rvContent;
    private RVAdapter raContent;
    private GridLayoutManager gridLayoutManager;
    private TextView txtUserName;
    private User user;
    private static Context context;
    private LinearLayout layout;
    private View recorder;
    private AudioEntryRecorder entryRecorder;
    private View playback;
    private AudioEntryPlayback entryPlayback;
    private ImageView ivMenu;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        gridLayoutManager = new GridLayoutManager(this, 3);
        //JController.setDatabase();
        setElements();
        setPermissions();
        tag = this.getClass().getName();

        setContentView(R.layout.activity_main);
        //updateContent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        JController.setCurrentActivity(this);
    }

    /**
     * Set UI element variables
     */
    private void setElements() {
        rvContent = (RecyclerView) findViewById(R.id.bookshelf);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav = (NavigationView) findViewById(R.id.nav);
        //ivMenu = (ImageView) findViewById(R.id.iv_menu);
        layout = (LinearLayout) findViewById(R.id.note_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toolbar.inflateMenu(R.menu.menu_main);
/*
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
*/
        NavigationViewHelper.enableNavigation(context,nav);
        nav.bringToFront();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                Intent intent = null;

                int id = menuItem.getItemId();

                if(id == R.id.addNote) {
                    intent = new Intent(context, ContentTaking.class);

                } else if (id == R.id.note) {
                    intent= new Intent(getApplicationContext(), Content_view.class);

                }

                context.startActivity(intent);

                return true;
            }
        });
    }

        //txtUserName = findViewById(R.id.txt_user_name);
        //layout = findViewById(R.id.audio_layout);




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iv_menu:
                Intent intent = new Intent(getApplicationContext(), Content_view.class);
                startActivity(intent);
                break;

            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void updateContent() {
        user = JController.getCurrentUser();
        if (user == null) {
            Log.v(tag, "User does not exist; null");
            return;
        }
        JController.setCurrentJournal(user.getJournal("0"));
        JController.setCurrentEntry(JController.getCurrentJournal().getEntry("0"));
        //txtUserName.setText(user.getName());
        //List<Content> ourContent = JController.getCurrentEntry().getListContent();

        List<Journal> journals = user.getListJournals();

        updateJournal(journals);
    }

    private void updateJournal(List<Journal> contents) {
        for (Journal j:contents) {
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT /* layout_width */, LinearLayout.LayoutParams.WRAP_CONTENT /* layout_height */, 1f /* layout_weight */);

            View view = new View(this);

            view.setLayoutParams(layoutParams);
            view.setForegroundGravity(Gravity.CENTER);

            layout.addView(view);

        }
    }

    private void clearRecycler() {
        rvContent.removeAllViewsInLayout();
    }

    public void updateUserContent(View view) {JController.databaseUpdateCurrentContent();}

    private void setPermissions() {
        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                    android.Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return first == getApplicationContext().getPackageManager().PERMISSION_GRANTED &&
                second == getApplicationContext().getPackageManager().PERMISSION_GRANTED;
    }


    public static Context getContext() {
        return context;
    }
}