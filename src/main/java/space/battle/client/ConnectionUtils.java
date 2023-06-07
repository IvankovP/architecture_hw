package space.battle.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import space.battle.core.utils.ThreadUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
//@Profile("ws")
public class ConnectionUtils {

    private final WebSocketClientHandler webSocketClientHandler;
    private final ObjectMapper objectMapper;

    public ConnectionUtils(WebSocketClientHandler webSocketClientHandler, ObjectMapper objectMapper) {
        this.webSocketClientHandler = webSocketClientHandler;
        this.objectMapper = objectMapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void connect() {
        // Установка соединения с сервером
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(webSocketClient, webSocketClientHandler, "ws://localhost:8080/websocket");
        connectionManager.start();
        System.out.println("Start");

        sendMessage();
    }

    public void sendMessages() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String next = sc.next();
            try {
                webSocketClientHandler.getSession().sendMessage(new TextMessage(next));

                if (next.equals("stop")) {
                    webSocketClientHandler.getSession().close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage() {
        try {
            while (webSocketClientHandler.getSession() == null) {
                ThreadUtils.sleep(1000);
            }

            Map<String, String> commandArgs = new HashMap<>();
            commandArgs.put("a", "1");
            commandArgs.put("b", "2");

            Map<String, String> message = new HashMap<>();
            message.put("gameId", "2");
            message.put("objectId", "5");
            message.put("commandId", "MoveCommand");
            message.put("commandArgs", getCommandArgs(commandArgs));

            webSocketClientHandler.getSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCommandArgs(Map<String, String> commandArgs) {
        try {
            return objectMapper.writeValueAsString(commandArgs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
