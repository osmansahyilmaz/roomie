package com.example.roomie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

public class AddExpenseActivity extends Activity {

    private EditText expenseNameEditText;
    private EditText expenseAmountEditText;
    private RadioGroup radioGroup1, radioGroup2;
    private Button addExpenseButton;
    private ImageButton backButton;

    Handler AddExpenseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addexpense_activity);

        expenseNameEditText = findViewById(R.id.editExpenseName);
        expenseAmountEditText = findViewById(R.id.editExpenseAmount);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        addExpenseButton = findViewById(R.id.btnAddExpense);
        backButton = findViewById(R.id.btnBack);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addExpense();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainGroupActivity
                Intent intent = new Intent(AddExpenseActivity.this, MainGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addExpense() throws JSONException {
        String expenseName = expenseNameEditText.getText().toString();
        String expenseAmount = expenseAmountEditText.getText().toString();
        RadioButton selectedRadioButton = findViewById(radioGroup1.getCheckedRadioButtonId());
        if (selectedRadioButton == null) {
            selectedRadioButton = findViewById(radioGroup2.getCheckedRadioButtonId());
        }
        String category = (selectedRadioButton != null) ? selectedRadioButton.getText().toString() : "Not specified";

        if (expenseName.isEmpty() || expenseAmount.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject expenseJSON = new JSONObject();
        expenseJSON.put("description", expenseName);
        expenseJSON.put("amount", expenseAmount);
        Toast.makeText(this, "Expense Added: " + expenseName + " for $" + expenseAmount + " in category: " + category, Toast.LENGTH_LONG).show();
        // Implement the actual expense saving logic here
        ExpenseRepository expenseRepo = new ExpenseRepository();
        ExecutorService srv = ((MainApplication)getApplication()).srv;
        Log.e("DEV:","addedExpenseeeee!");
        expenseRepo.addExpense(srv,AddExpenseHandler,expenseJSON);
    }
}
