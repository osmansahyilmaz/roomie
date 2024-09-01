package com.example.roomie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;

public class SignUpActivity extends Activity {

    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;
    private ImageButton backButton;

    Handler SignUpHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(SignUpActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, JoinGroupActivity.class);
                    startActivity(intent);
                    finish(); // Optionally finish this activity if you don't want it in the back stack
                    return true;

                case 0:
                    String errorMessage = (String) msg.obj;
                    Toast.makeText(SignUpActivity.this, "Registration Failed: Username or Email is already taken!", Toast.LENGTH_SHORT).show();
                    return true;

                default:
                    return false;
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup_activity);

        // Initialize fields
        emailEditText = findViewById(R.id.loginUsername);
        usernameEditText = findViewById(R.id.editUsername);
        passwordEditText = findViewById(R.id.loginPassword);
        confirmPasswordEditText = findViewById(R.id.editConfirmPassword);
        signUpButton = findViewById(R.id.btnSignUp);
        backButton = findViewById(R.id.btnBack);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to LoginActivity
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void attemptSignUp() {
        String email = emailEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Password fields cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthRepository repo = new AuthRepository();
        ExecutorService srv = ((MainApplication)getApplication()).srv;
        String name = "dummy name";
        repo.signUp(srv,SignUpHandler,name,username,email,password);

    }
}
