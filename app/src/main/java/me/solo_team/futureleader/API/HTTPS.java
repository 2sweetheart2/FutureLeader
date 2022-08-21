package me.solo_team.futureleader.API;

import android.graphics.Bitmap;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * все изходящие запросы могут быть обработы только один раз
     * в случае создания двух слушателей на один и тот же запрос, будет задействован только первый слушатель
     */
    private static final Procesor procesor = new Procesor();

    public static class Procesor {
        private List<ApiListener> shop = new ArrayList<>();
        public HashMap<Methods, ApiListener> queue = new HashMap<>();
    }

    public interface GetToken {
        void process(String token) throws JSONException;
    }


    public static void getMobileToken(GetToken callback) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    try {
                        if (!task.isSuccessful()) {
                            callback.process(null);
                            return;
                        }
                        callback.process(task.getResult());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
    }


    public static void sendPost(Methods method, JSONObject params, ApiListener apiListener) {
        if (procesor.queue.containsKey(method) && method != Methods.ADD_SHOP_REQUEST) return;
        else {
            if (method == Methods.ADD_SHOP_REQUEST) {
                procesor.shop.add(apiListener);
            } else
                procesor.queue.put(method, apiListener);
        }
        apiListener.inProcess();
        getMobileToken(token -> {
            if (token == null) {
                try {
                    JSONObject o = new JSONObject();
                    o.put("success", false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o.put("error", b);
                    procesor.queue.get(method).process(o);
                    procesor.queue.remove(method);
                } catch (JSONException ignored) {
                }
                return;
            }
            params.put("mobile_token", token);
            new Thread(() -> {
                try {
                    HttpURLConnection con = (HttpsURLConnection) new URL(URL + method.label).openConnection();
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; utf-8");
                    con.setRequestProperty("Accept", "application/json");
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
                        ApiListener met;
                        if (method == Methods.ADD_SHOP_REQUEST) {
                            met = procesor.shop.get(0);
                            procesor.shop.remove(0);
                        } else {
                            met = procesor.queue.get(method);
                            procesor.queue.remove(method);
                        }
                        met.process(new JSONObject(response_.toString()));

                    }
                } catch (IOException | JSONException e) {
                    try {
                        JSONObject o = new JSONObject();
                        o.put("success", false);
                        JSONObject b = new JSONObject();
                        b.put("message", "can't connect to server");
                        o.put("error", b);
                        procesor.queue.get(method).process(o);
                        procesor.queue.remove(method);
                    } catch (JSONException ignored) {
                    }
                }
            }).start();
        });
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


    public static void u(Methods method, JSONObject data, Bitmap image, ApiListener callback) {
        callback.inProcess();
        JSONObject o = new JSONObject();
        getMobileToken(token -> {
            if (token == null) {
                try {
                    JSONObject o1 = new JSONObject();
                    o1.put("success", false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o1.put("error", b);
                    procesor.queue.get(method).process(o1);
                    procesor.queue.remove(method);
                } catch (JSONException ignored) {
                }
                return;
            }
            data.put("mobile_token", token);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            callback.process(o);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        callback.process(o);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).start();
        });


    }

    public static void sendVideoFile(Methods method, JSONObject data, File file, ApiListener callback) {
        callback.inProcess();
        JSONObject o = new JSONObject();
        getMobileToken(token -> {
            if (token == null) {
                try {
                    JSONObject o1 = new JSONObject();
                    o1.put("success", false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o1.put("error", b);
                    procesor.queue.get(method).process(o1);
                    procesor.queue.remove(method);
                } catch (JSONException ignored) {
                }
                return;
            }
            data.put("mobile_token", token);
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
                    String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.

                    URLConnection connection = new URL(URL + method.label).openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    try (OutputStream output = connection.getOutputStream();
                         PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);
                    ) {
                        writer.append("--" + boundary).append("\r\n");
                        writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + file.getName() + "\"").append("\r\n");
                        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append("\r\n");
                        writer.append("Content-Transfer-Encoding: binary").append("\r\n");
                        writer.append("\r\n").flush();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Files.copy(file.toPath(), output);
                        }
                        output.flush(); // Important before continuing with writer!
                        writer.append("\r\n").flush(); // CRLF is important! It indicates end of boundary.

                        // End of multipart/form-data.
                        writer.append("--" + boundary + "--").append("\r\n").flush();

                        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                            StringBuilder response_ = new StringBuilder();
                            String responseLine;
                            while ((responseLine = br.readLine()) != null) {
                                response_.append(responseLine.trim());
                            }
                            callback.process(new JSONObject(response_.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                callback.process(o);
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });


    }

    public static void sendAudio(Methods method, JSONObject data, byte[] bytes, String name, ApiListener callback) {
        callback.inProcess();
        JSONObject o = new JSONObject();
        getMobileToken(token -> {
            if (token == null) {
                try {
                    JSONObject o1 = new JSONObject();
                    o1.put("success", false);
                    JSONObject b = new JSONObject();
                    b.put("message", "can't connect to server");
                    o1.put("error", b);
                    procesor.queue.get(method).process(o1);
                    procesor.queue.remove(method);
                } catch (JSONException ignored) {
                }
                return;
            }
            data.put("mobile_token", token);
            try {
                o.put("success", false);
                JSONObject b = new JSONObject();
                b.put("message", "can't connect to server");
                o.put("error", b);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(data.toString(1));

            new Thread(() -> {
                try {
                    HttpURLConnection connection = (HttpsURLConnection) new URL(URL + method.label).openConnection();
                    connection.setRequestMethod("POST");
                    String boundary = UUID.randomUUID().toString();

                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    DataOutputStream request = new DataOutputStream(connection.getOutputStream());
                    request.writeBytes("--" + boundary + "\r\n");
                    request.writeBytes("Content-Disposition: form-data; name=\"audio\"; filename=\"" + name + "\"\r\n\r\n");
                    request.write(bytes);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            callback.process(o);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        callback.process(o);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).start();
        });


    }

}
