package com.example.journallybackendtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends JournallyActivity {
    private EditText txtName, txtPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setElements();
    }

    protected void onResume() {
        super.onResume();
        JController.setCurrentActivity(this);
    }

    @Override
    void updateContent() {
        // Nothing
        System.out.println(JController.getCurrentUser().getName());
    }

    private void setElements() {
        txtName = findViewById(R.id.txt_name);
        txtPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String password = txtPassword.getText().toString();
                JController.changeCurrentUser(name, password);
            }
        });
    }
}