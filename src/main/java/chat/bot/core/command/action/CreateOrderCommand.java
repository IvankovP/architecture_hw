package chat.bot.core.command.action;

import chat.bot.core.command.Command;
import chat.bot.core.service.OrderService;
import chat.bot.core.utils.IoC;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class CreateOrderCommand implements Command {

    private final WebSocketSession session;
    private final String[] params;

    public CreateOrderCommand(WebSocketSession session, String[] params) {
        this.session = session;
        this.params = params;
    }

    @Override
    public void execute() throws IOException {
        OrderService orderService = IoC.resolve("orderService");
        if (orderService != null) {
            int order = orderService.createOrder();
            session.sendMessage(new TextMessage("Order was created " + order));
        }
    }
}
