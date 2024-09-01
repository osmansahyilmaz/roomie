package com.example.roomie;

import android.os.Handler;
import android.os.Message;

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
import java.util.concurrent.ExecutorService;

public class AuthRepository {


    public void signUp(ExecutorService srv, Handler uiHandler, String name, String username, String email, String password) {
        srv.execute(() -> {

            try {
                URL url = new URL("http://192.168.50.145:8080/roomies/auth/register");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject objToSend = new JSONObject();
                objToSend.put("name",name);
                objToSend.put("username",username);
                objToSend.put("email",email);
                objToSend.put("password",password);

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

    public void logIn(ExecutorService srv, Handler uiHandler,String username, String password) {
        srv.execute(() -> {

            try {
                URL url = new URL("http://192.168.50.145:8080/roomies/auth/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject objToSend = new JSONObject();
                objToSend.put("username",username);
                objToSend.put("password",password);

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
