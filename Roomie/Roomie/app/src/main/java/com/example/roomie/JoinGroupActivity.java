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
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class JoinGroupActivity extends Activity {

    private EditText groupIDEditText;
    private Button joinGroupButton;
    private Button createGroupButton;
    private ImageButton backButton;

    Handler JoinGroupHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_joingroup_activity);

        groupIDEditText = findViewById(R.id.editGroupID);
        joinGroupButton = findViewById(R.id.btnJoinGroup);
        createGroupButton = findViewById(R.id.btnCreateGroup);
        backButton = findViewById(R.id.btnBack);

        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupID = groupIDEditText.getText().toString();
                joinGroup(groupID);
            }
        });

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to LoginActivity
                Intent intent = new Intent(JoinGroupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void joinGroup(String groupID) {
        if (groupID.isEmpty()) {
            Toast.makeText(this, "Please enter a Group ID to join.", Toast.LENGTH_SHORT).show();
            return;
        }
        GroupRepository groupRepo = new GroupRepository();
        ExecutorService srv = Executors.newSingleThreadExecutor();
        groupRepo.joinGroup(srv,JoinGroupHandler, groupID);

        // Here, you should implement the actual logic to check if the group ID is valid
        // This could involve querying your backend or database
        // For now, we'll assume the ID is valid for demonstration purposes
        Toast.makeText(this, "Joining group...", Toast.LENGTH_SHORT).show();

        // Assuming the group join is successful, transition to MainGroupActivity
        Intent intent = new Intent(JoinGroupActivity.this, MainGroupActivity.class);
        startActivity(intent);
    }

    private void createGroup() {
        // Navigate to CreateGroupActivity
        Intent intent = new Intent(JoinGroupActivity.this, GroupCreatingActivity.class);
        startActivity(intent);
    }
}
