package com.example.roomie;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroupRepository {
    public boolean HasGroup = false;
    Handler GroupHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    //Intent intent = new Intent(LoginActivity.class, JoinGroupActivity.class);
                    //startActivity(intent);
                    //finish(); // Optionally finish this activity if you don't want it in the back stack

                    HasGroup = true;
                    return true;

                case 0:
                    String errorMessage = (String) msg.obj;
                    //Toast.makeText(LoginActivity.class.newInstance(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                    HasGroup = false;
                    return false;

                default:
                    return false;
            }
        }


    });

    public void CheckUserHasGroup(ExecutorService srv, String username, AtomicBoolean itHas) {
        srv.execute(() -> {

            try {
                String userID= "665e8a02f5708908c567a608";
                URL url = new URL("http://192.168.50.145:8080/roomies/groups/" + userID);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


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


                Log.i("DEV", "HasGroup: " + returnMessage);
                Message msg = new Message();
                if (returnMessage.equals("OK")) {
                    HasGroup = true;
                    itHas.set(true);
                    Log.i("DEV", "joinedthere: " + returnMessage);
                }
                else {
                    HasGroup = false;
                    itHas.set(false);
                    Log.i("DEV", "joinedthere: " + returnMessage);

                }
                msg.obj = returnMessage;


                GroupHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });
    }

    public void createGroup(ExecutorService srv, Handler uiHandler, String groupName, String username) {
        srv.execute(() -> {

            try {
                URL url = new URL("http://192.168.50.145:8080/roomies/groups/create-group");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject objToSend = new JSONObject();
                objToSend.put("name",groupName);
                objToSend.put("username",username);

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
                msg.obj = retVal.getString("data");


                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });
    }

    public void joinGroup(ExecutorService srv, Handler uiHandler, String groupCode) {
        srv.execute(() -> {

            try {
                String userID = "6660c7e5a608ec05906b5793";
                URL url = new URL("http://192.168.50.145:8080/roomies/groups/" + groupCode + "/join?userId=" + userID);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject objToSend = new JSONObject();

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

                msg.obj = retVal.getJSONObject("data");


                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });
    }
}
