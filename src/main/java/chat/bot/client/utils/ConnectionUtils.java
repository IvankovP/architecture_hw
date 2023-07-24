package chat.bot.client.utils;

import chat.bot.client.websocket.WebSocketClientHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.Scanner;

@Component
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

        sendMessages();
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
}
