package com.example.journallybackendtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {
    private EditText txtName, txtPassword;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setElements();
        cleanElements();
    }

    private void setElements() {
        txtName = findViewById(R.id.txt_name_registration);
        txtPassword = findViewById(R.id.txt_password_registration);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation()) { return; }
                String name = txtName.getText().toString();
                String password = txtName.getText().toString();
                User newUser = JController.createUser(name, password);
                JController.insertUser(newUser);
                Return();
            }
        });
    }

    private boolean validation() {
        String name = txtName.getText().toString();
        String password = txtName.getText().toString();
        return (!name.equals("") && !password.equals(""));
    }

    private void cleanElements() {
        txtName.setText("");
        txtPassword.setText("");
    }

    private void Return() {
        finish();
    }
}