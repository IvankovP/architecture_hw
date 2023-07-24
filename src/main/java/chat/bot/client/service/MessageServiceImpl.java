package chat.bot.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import chat.bot.client.websocket.WebSocketClientHandler;

import java.io.IOException;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final WebSocketClientHandler handler;
    private final ObjectMapper objectMapper;

    public MessageServiceImpl(WebSocketClientHandler handler, ObjectMapper objectMapper) {
        this.handler = handler;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(Map<String, String> message) throws IOException {
        handler.getSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }
}
