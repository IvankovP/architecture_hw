package chat.bot.core.websocket;

import chat.bot.core.command.support.InterpretCommand;
import chat.bot.core.service.OrderService;
import chat.bot.core.utils.CommandRunner;
import chat.bot.core.utils.IoC;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final OrderService orderService;
    private final CommandRunner commandRunner;

    public CustomWebSocketHandler(OrderService orderService, CommandRunner commandRunner) {
        this.orderService = orderService;
        this.commandRunner = commandRunner;
    }

    @PostConstruct
    void post() {
        IoC.register("orderService", orderService);
        IoC.register("commandRunner", commandRunner);
        commandRunner.run();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Client: " + message.getPayload());
        commandRunner.addCommand(new InterpretCommand(session, message));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connect to client");
    }
}
