package space.battle.core.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import space.battle.core.Game;
import space.battle.core.IoC;
import space.battle.core.command.Command;
import space.battle.core.command.support.InterpretCommand;
import space.battle.core.service.GameService;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ReentrantLock lock = new ReentrantLock();
    private final GameService gameService;

    public CustomWebSocketHandler(ObjectMapper objectMapper, GameService gameService) {
        this.objectMapper = objectMapper;
        this.gameService = gameService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        lock.lock();

        System.out.println("Received message from client: " + message.getPayload());

        HashMap<String, String> param;
        HashMap<String, String> commandArgs;

        try {
            param = objectMapper.readValue(message.getPayload(), HashMap.class);
            commandArgs = objectMapper.readValue(param.get("commandArgs"), HashMap.class);
            validateToken(param);
        } finally {
            lock.unlock();
        }

        Command command = createCommand(param, commandArgs);
        addCommandToGame(param, command);

        // Отправка ответа клиенту
        session.sendMessage(new TextMessage("Received message: " + message.getPayload()));
    }

    private void validateToken(HashMap<String, String> param) {
        gameService.validateToken(param.get("token"));
    }

    private void addCommandToGame(HashMap<String, String> param, Command command) {
        try {
            Game game = IoC.resolve("game", param.get("gameId"));
            if (game != null) {
                game.getCommands().add(command);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Command createCommand(HashMap<String, String> param, HashMap<String, String> commandArgs) {
        return new InterpretCommand(param, commandArgs);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connect to agent");
    }
}
