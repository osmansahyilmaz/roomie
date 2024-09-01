package com.example.roomie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class GroupCreatingActivity extends Activity {

    private EditText groupNameEditText;
    private Button createGroupButton;
    private ImageButton backButton;

    Handler GroupCreatingHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_groupcreating_activity);

        groupNameEditText = findViewById(R.id.editGroupName);
        createGroupButton = findViewById(R.id.btnCreateGroup);
        backButton = findViewById(R.id.btnBack);

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = groupNameEditText.getText().toString().trim();
                String username = getUsername();

                createGroup(groupName, username);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to JoinGroupActivity
                Intent intent = new Intent(GroupCreatingActivity.this, JoinGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getUsername() {
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPref.getString("username", "defaultUsername"); // Default value if not found
    }
    private void createGroup(String groupName, String username) {
        if (groupName.isEmpty()) {
            Toast.makeText(this, "Group name cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        GroupRepository groupRepo = new GroupRepository();
        ExecutorService srv = ((MainApplication)getApplication()).srv;
        groupRepo.createGroup(srv,GroupCreatingHandler,groupName,username);
        // Here, implement the logic to actually create the group in your database or backend
        // Assume this process is successful and proceed to navigate to MainGroupActivity
        Toast.makeText(this, "Group '" + groupName + "' created successfully!", Toast.LENGTH_LONG).show();

        // Navigate to MainGroupActivity
        Intent intent = new Intent(GroupCreatingActivity.this, MainGroupActivity.class);
        startActivity(intent);
        finish(); // Optionally finish this activity if you don't want it in the back stack
    }
}
