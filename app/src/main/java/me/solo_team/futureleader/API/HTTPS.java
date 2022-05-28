package me.solo_team.futureleader.API;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

/**
 * реквесты на сервер
 *
 * @author Фома
 */
public class HTTPS {
    // TODO: ТУТ НИЧЕГО НЕ МЕНЯТЬ!!!
    private static final String URL = "https://future-leaders.ru/api/";


    public static void sendPost(Methods method, JSONObject params, ApiListener apiListener) {
        new Thread(() -> {
            try {
                HttpURLConnection con = (HttpsURLConnection) new URL(URL + method.label).openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);
                System.out.println(con.getURL());
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = params.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response_ = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response_.append(responseLine.trim());
                    }
                    apiListener.process(new JSONObject(response_.toString()));
                }
            } catch (IOException | JSONException e) {
                try {
                    JSONObject o = new JSONObject();
                    o.put("success", false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o.put("error", b);
                    apiListener.process(o);
                } catch (JSONException ignored) {
                }
            }
        }).start();
    }

    public static void sendPost(Methods method, JSONObject params, FullApiListener apiListener) {
        if (method == Methods.UPD_PROFILE_PICTURE)
            apiListener.inProgress();

        new Thread(() -> {
            try {
                HttpURLConnection con = (HttpsURLConnection) new URL(URL + method.label).openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = params.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response_ = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response_.append(responseLine.trim());
                    }
                    apiListener.process(new JSONObject(response_.toString()));
                }
            } catch (IOException | JSONException e) {
                try {
                    JSONObject o = new JSONObject();
                    o.put("success", false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o.put("error", b);
                    apiListener.process(o);
                } catch (JSONException ignored) {
                }
            }
        }).start();
    }


    public static void u(Methods method, JSONObject data, Bitmap image, FullApiListener callback) {
        callback.inProgress();
        JSONObject o = new JSONObject();
        try {
            o.put("success", false);
            JSONObject b = new JSONObject();
            b.put("message", "can't connect to server");
            o.put("error", b);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                HttpURLConnection connection = (HttpsURLConnection) new URL(URL + method.label).openConnection();
                connection.setRequestMethod("POST");
                String boundary = UUID.randomUUID().toString();

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                DataOutputStream request = new DataOutputStream(connection.getOutputStream());
                request.writeBytes("--" + boundary + "\r\n");
                request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"file.bmp\"\r\n\r\n");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                request.write(byteArray);
                request.writeBytes("\r\n");
                for (int i = 0; i < data.names().length(); i++) {
                    request.writeBytes("--" + boundary + "\r\n");
                    request.writeBytes("Content-Disposition: form-data; name=\"" + data.names().getString(i) + "\"\r\n\r\n");
                    request.writeBytes(data.get(data.names().getString(i)) + "\r\n");
                }
                request.writeBytes("--" + boundary + "--\r\n");
                request.flush();

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response_ = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response_.append(responseLine.trim());
                    }
                    callback.process(new JSONObject(response_.toString()));
                }catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        callback.process(o);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            } catch(IOException e){
            e.printStackTrace();
                try {
                    callback.process(o);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
        } catch (JSONException e) {
                e.printStackTrace();
            }
        }).

    start();

}

}
