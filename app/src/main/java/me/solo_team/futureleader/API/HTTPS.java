package me.solo_team.futureleader.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * реквесты на сервер
 * @author Фома
 */
public class HTTPS {
    // TODO: ТУТ НИЧЕГО НЕ МЕНЯТЬ!!!
    private static final String URL = "https://future-leader.ru/api/";


    public static void sendPost(Methods method, JSONObject params, ApiHandler apiHandler) {
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
                    apiHandler.process(new JSONObject(response_.toString()));
                }
            } catch (IOException | JSONException e) {
                try {
                    JSONObject o = new JSONObject();
                    o.put("success",false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o.put("error",b);
                    apiHandler.process(o);
                } catch (JSONException ignored) {}
            }
        }).start();
    }

}
