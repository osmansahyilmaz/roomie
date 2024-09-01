package com.example.roomie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.content.Context;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class ExpenseRepository {

    private static final String BASE_URL = "http://192.168.50.145:8080/roomies/expenses/";
    List<Expense> data;
    // Method to fetch all expenses for a user
    public void fetchExpenses(ExecutorService executorService, Handler handler, String groupId) {
        executorService.execute(() -> {
            try {
                data = new ArrayList<>();

                URL url = new URL(BASE_URL + groupId);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONObject arrObject = new JSONObject(buffer.toString());
                JSONArray arr = arrObject.getJSONArray("data");

                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject current = arr.getJSONObject(i);

                    JSONObject currentCategory = current.getJSONObject("category");
                    String categoryName;
                    if (currentCategory.has("name")) {
                        categoryName = currentCategory.getString("name");
                    } else {
                        categoryName = "Unknown";
                    }
                    Expense new_expense = new Expense(current.getString("description"),
                            current.getDouble("amount"),
                            categoryName,
                            LocalDate.now());

                    Log.e("DEV", data.toString());
                    data.add(new_expense);
                }
                Message msg = new Message();
                msg.obj = data;

                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException | JSONException e) {
                Log.e("DEV", Objects.requireNonNull(e.getMessage()));
            }


        });

    }
    // Method to add a new expense
    public void addExpense(ExecutorService executorService, Handler handler, JSONObject expenseDetails) {
        executorService.execute(() -> {
            try {
                URL url = new URL(BASE_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject objToSend = new JSONObject();
                objToSend.put("username","selin");
                objToSend.put("description",expenseDetails.getString("description"));
                objToSend.put("amount",expenseDetails.getString("amount"));
                objToSend.put("categoryName","toFill");

                //String outputData = "{\"name\":\""+ name +"\",\"lastname\":\""+ lastname+"\"}";

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());

                writer.write(objToSend.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());


                String returnMessage= retVal.getString("status");

                conn.disconnect();



                Message msg = new Message();
                if (returnMessage.equals("OK")) {
                    msg.what = 1;
                }
                else {
                    msg.what = 0;
                }
                msg.obj = returnMessage;


                handler.sendMessage(msg);
            } catch (Exception e) {
                Log.e("ExpenseRepository", "Error adding expense", e);
            }
        });
    }


}