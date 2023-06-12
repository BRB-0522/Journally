package com.example.journallybackendtest;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class NavigationViewHelper {
   public static void enableNavigation(final Context context, NavigationView view){
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.addNote){
                    Intent intent=new Intent(context, ContentTaking.class);
                    context.startActivity(intent);
                }else if(item.getItemId()==R.id.journal){
                    Intent intent=new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                }else if(item.getItemId()==R.id.note){
                    Intent intent=new Intent(context, JournalView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                }
                return false;
            }
        });
    }
}