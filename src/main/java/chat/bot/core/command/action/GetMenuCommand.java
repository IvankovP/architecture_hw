package chat.bot.core.command.action;

import chat.bot.core.command.Command;
import chat.bot.core.utils.IoC;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GetMenuCommand implements Command {

    private final WebSocketSession session;
    private final String[] params;

    public GetMenuCommand(WebSocketSession session, String[] params) {
        this.session = session;
        this.params = params;
    }

    @Override
    public void execute() throws IOException {
        List<String> menu = IoC.resolve("menuList");
        session.sendMessage(new TextMessage("Menu:" + System.lineSeparator() + (CollectionUtils.isEmpty(menu) ? "отсутствует" : menu)));
    }
}
