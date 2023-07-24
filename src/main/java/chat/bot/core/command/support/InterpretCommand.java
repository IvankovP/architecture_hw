package chat.bot.core.command.support;

import chat.bot.core.command.Command;
import chat.bot.core.utils.CommandRunner;
import chat.bot.core.utils.IoC;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.function.BiFunction;

public class InterpretCommand implements Command {

    private final WebSocketSession session;
    private final TextMessage message;

    public InterpretCommand(WebSocketSession session, TextMessage message) {
        this.session = session;
        this.message = message;
    }

    @Override
    public void execute() {
        String[] split = message.getPayload().split(", ");
        if (split.length == 0) {
            return;
        }

        BiFunction<WebSocketSession, String[], Command> function = IoC.resolve(split[0]);
        if (function != null) {
            Command command = function.apply(session, split);
            CommandRunner commandRunner = IoC.resolve("commandRunner");
            commandRunner.addCommand(command);
        }
    }
}
