package chat.bot.core.command.action;

import chat.bot.core.adapter.OrderAdapter;
import chat.bot.core.command.Command;
import chat.bot.core.entity.IOrder;
import chat.bot.core.entity.Position;
import chat.bot.core.entity.UObject;
import chat.bot.core.entity.UObjectImpl;
import chat.bot.core.utils.IoC;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

public class AddPositionCommand implements Command {

    private final WebSocketSession session;
    private final String[] params;

    public AddPositionCommand(WebSocketSession session, String[] params) {
        this.session = session;
        this.params = params;
    }

    @Override
    public void execute() throws IOException {
        UObject order = IoC.resolve("order");
        if (order == null) {
            order = new UObjectImpl();
            order.setProperty("positions", new ArrayList<Position>());
            IoC.register("order", order);
        }
        new OrderAdapter(order).addPosition(params[1], Integer.parseInt(params[2]));
        session.sendMessage(new TextMessage("Added position " + params[1] + ", count " + params[2]));
    }
}
