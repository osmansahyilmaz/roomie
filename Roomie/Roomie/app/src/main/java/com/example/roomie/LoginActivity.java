package com.example.roomie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends Activity {
    private EditText loginUsername;
    private EditText loginPassword;
    private Button signInButton;
    private Button signUpButton;
    public boolean loggedIn = false;
    Handler LogInHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    loggedIn = true;
                    return true;

                case 0:
                    String errorMessage = (String) msg.obj;
                    Toast.makeText(LoginActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
                    loggedIn = false;
                    return false;

                default:
                    return false;
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_activity);

        loginUsername = findViewById(R.id.loginUsername); // Assuming you have this EditText in layout_login_activity.xml
        loginPassword = findViewById(R.id.loginPassword); // Assuming you have this EditText in layout_login_activity.xml
        signInButton = findViewById(R.id.btnSignIn);
        signUpButton = findViewById(R.id.btnSignUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        String username = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password fields cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }


        AuthRepository repo = new AuthRepository();
        ExecutorService srv = ((MainApplication)getApplication()).srv;
        repo.logIn(srv,LogInHandler,username,password);

        if (loggedIn) { // Assume these credentials are valid
            // Login successful
            Log.i("DEV", "Login successfulllllllllll: ");
            checkUserGroupStatus(username);

        } else {
            // Login failed
        }
    }

    private void checkUserGroupStatus(String username) {
        storeUsername(username);
        // Placeholder for checking if the user is part of any group
        // This should interface with your database or backend
        GroupRepository groupRepo = new GroupRepository();
        ExecutorService srv = Executors.newSingleThreadExecutor();
        AtomicBoolean itHas = new AtomicBoolean(false);
        groupRepo.CheckUserHasGroup(srv, username, itHas);

        srv.shutdown();
        try {
            srv.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean HasGroup = itHas.get();
        Log.i("DEV", "HasGrouphere: " + HasGroup);

        if (HasGroup) {
            Toast.makeText(LoginActivity.this, "User Has Group!", Toast.LENGTH_SHORT).show();
            // Navigate to MainGroupActivity if user is part of a group
            Intent intent = new Intent(LoginActivity.this, MainGroupActivity.class);
            startActivity(intent);
        } else {
            // Navigate to JoinGroupActivity if user is not part of any group
            Intent intent = new Intent(LoginActivity.this, JoinGroupActivity.class);
            startActivity(intent);
        }
        finish(); // Finish LoginActivity after handling navigation
    }

    private void storeUsername(String username) {
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.apply();
    }
}
