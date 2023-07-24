package chat.bot.core.config;

import chat.bot.core.command.Command;
import chat.bot.core.command.action.AddPositionCommand;
import chat.bot.core.command.action.CreateOrderCommand;
import chat.bot.core.command.action.DeleteOrderCommand;
import chat.bot.core.command.action.GetMenuCommand;
import chat.bot.core.exception.CommandExceptionHandler;
import chat.bot.core.utils.CommandRunner;
import chat.bot.core.utils.IoC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;

@Configuration
public class Config {

    static {
        BiFunction<WebSocketSession, String[], Command> createOrderFunction = CreateOrderCommand::new;
        IoC.register("create", createOrderFunction);
        BiFunction<WebSocketSession, String[], Command> deleteOrderFunction = DeleteOrderCommand::new;
        IoC.register("delete", deleteOrderFunction);
        BiFunction<WebSocketSession, String[], Command> addPositionFunction = AddPositionCommand::new;
        IoC.register("add", addPositionFunction);
        BiFunction<WebSocketSession, String[], Command> getMenuFunction = GetMenuCommand::new;
        IoC.register("menu", getMenuFunction);
    }

    @Bean
    public List<String> menu() {
        List<String> menu = Arrays.asList("Food1", "Food2", "Food3", "Food4", "Food5", "Food6", "Food7");
        IoC.register("menuList", menu);
        return menu;
    }

    @Bean
    public CommandRunner commandRunner() {
        LinkedBlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        return new CommandRunner(new LinkedBlockingQueue<>(), new CommandExceptionHandler(commands, new HashMap<>()));
    }
}
