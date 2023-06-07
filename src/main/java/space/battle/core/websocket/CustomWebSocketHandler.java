package space.battle.core.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import space.battle.core.Game;
import space.battle.core.command.support.InterpretCommand;
import space.battle.core.service.GameService;

import java.util.HashMap;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final GameService gameService;

    public CustomWebSocketHandler(ObjectMapper objectMapper, GameService gameService) {
        this.objectMapper = objectMapper;
        this.gameService = gameService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //todo synchronize
        System.out.println("Received message from client: " + message.getPayload());

        try {
            validateToken(message);
            createAndAddCommandToGame(message);

        } catch (Exception e) {
            // Отправка ответа клиенту
            session.sendMessage(new TextMessage("Error process message: " + e.getMessage()));
            return;
        }

        // Отправка ответа клиенту
        session.sendMessage(new TextMessage("Received message: " + message.getPayload()));
    }

    private void validateToken(TextMessage message) throws JsonProcessingException {
        HashMap<String, String> param = objectMapper.readValue(message.getPayload(), HashMap.class);
        gameService.validateToken(param.get("token"));
    }

    private void createAndAddCommandToGame(TextMessage message) throws JsonProcessingException {
        HashMap<String, String> param = objectMapper.readValue(message.getPayload(), HashMap.class);
        HashMap<String, String> commandArgs = objectMapper.readValue(param.get("commandArgs"), HashMap.class);

        Game game = gameService.getGame(Integer.parseInt(param.get("gameId")));
        game.getCommands().add(new InterpretCommand());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connect to agent");
    }
}
