package com.example.roomie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

public class MainGroupActivity extends Activity {

    private Button addExpenseButton;
    private ListView listViewExpenses;
    private ArrayList<Expense> expenses;
    private ExpenseAdapter adapter;
    private ExecutorService executorService;
    private ExpenseRepository expenseRepository;
    private Handler handler;
    private JSONArray expensesArray;

    // Setup handler
    Handler MainGroupHandler = new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    expensesArray = new JSONArray(msg.obj);
                    Log.e("HEY", "BURAYA GIRIDI");
                    for (int i = 0; i < expensesArray.length(); i++) {
                        Log.i("Expense", expensesArray.getJSONObject(i).toString());
                    }

                    updateExpensesList(expensesArray);
                } catch (JSONException e) {
                    Toast.makeText(MainGroupActivity.this, "Error parsing expenses", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainGroupActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_maingroup_activity);

        // Initialize buttons, ListView, and executor service
        addExpenseButton = findViewById(R.id.btnAddExpense);
        listViewExpenses = findViewById(R.id.listViewExpenses);
        executorService = Executors.newSingleThreadExecutor();
        expenseRepository = new ExpenseRepository();
        expenses = new ArrayList<>();
        adapter = new ExpenseAdapter(this, expenses);
        listViewExpenses.setAdapter(adapter);
        // Load expenses from the server

        try {
            loadExpenses();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ArrayAdapter<Expense> arr;
        arr = new ArrayAdapter<Expense>( this,  R.layout.item_expense, expenses);
        listViewExpenses.setAdapter(arr);

        // Setup OnClickListener for the Add Expense Button
        addExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainGroupActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });


    }

    private void loadExpenses() throws JSONException {
        String groupId = "665e861ff5708908c567a5fc"; // Replace with the actual group ID
        expenseRepository.fetchExpenses(executorService, MainGroupHandler, groupId);
    }

    private void updateExpensesList(JSONArray expensesData) throws JSONException {

        expenses.clear();
        for (int i = 0; i < expensesData.length(); i++) {
            JSONObject obj = expensesData.getJSONObject(i);
            JSONObject category = obj.getJSONObject("category");
            String categoryName;
            if (category.has("name")) {
                categoryName = category.getString("name");
            } else {
                categoryName = "Unknown";
            }
            Expense expense = new Expense(
                    obj.getString("description"),
                    obj.getDouble("amount"),
                    categoryName,
                    LocalDate.parse(obj.getString("time"))  // Ensure date format matches
            );
            Log.i("Expense", expense.toString());
            expenses.add(expense);
        }
        adapter.notifyDataSetChanged();
}
}