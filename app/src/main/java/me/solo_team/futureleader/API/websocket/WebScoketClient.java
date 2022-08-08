package me.solo_team.futureleader.API.websocket;

import io.socket.client.IO;
import io.socket.client.Socket;
import me.solo_team.futureleader.Constants;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebScoketClient {

    public static Socket mSocket;


    public static void createConnection() {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Collections.singletonList("Bearer " + Constants.user.token));
        headers.put("FBS-Token",Collections.singletonList(Constants.user.mobileToken));
        try {
            IO.Options options = IO.Options.builder()
                    .setExtraHeaders(headers)
                    .setPath("/future-leaders/websocket")
                    .setTimeout(10000)
                    .build();
            mSocket = IO.socket("https://future-leaders.ru", options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
    }
}
