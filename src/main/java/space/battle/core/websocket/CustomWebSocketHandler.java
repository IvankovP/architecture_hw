package space.battle.core.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    public CustomWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //todo synchronize
        System.out.println("Received message from client: " + message.getPayload());

        createCommand(message);

        // Отправка ответа клиенту
        session.sendMessage(new TextMessage("Received message: " + message.getPayload()));
    }

    private void createCommand(TextMessage message) throws JsonProcessingException {
        HashMap<String, String> param = objectMapper.readValue(message.getPayload(), HashMap.class);
        HashMap<String, String> commandArgs = objectMapper.readValue(param.get("commandArgs"), HashMap.class);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connect to agent");
    }
}
