package space.battle.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import space.battle.client.service.MessageService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ClientTest {

    @Autowired
    private WebSocketClientHandler handler;

    @Test
    void sendMessageTest() throws IOException {
        // Установка соединения с сервером
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(webSocketClient, handler, "ws://localhost:8080/websocket");
        connectionManager.start();
        System.out.println("Start");

        handler.getSession().sendMessage(new TextMessage("sfsdfsdf"));
    }
}
