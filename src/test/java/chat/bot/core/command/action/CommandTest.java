package chat.bot.core.command.action;

import chat.bot.core.command.Command;
import chat.bot.core.command.support.InterpretCommand;
import chat.bot.core.config.Config;
import chat.bot.core.utils.CommandRunner;
import chat.bot.core.utils.IoC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private final Config config = new Config();

    @BeforeEach
    void setUp() {
        IoC.register("commandRunner", config.commandRunner());
    }

    @Test
    void CreateOrderCommand() throws Exception {
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("create"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(1, commandRunner.getCommands().size());
        assertEquals(CreateOrderCommand.class, commandRunner.getCommands().poll().getClass());
    }

    @Test
    void CreateOrderCommandFail() throws Exception {
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("createOrder"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(0, commandRunner.getCommands().size());
    }

    @Test
    void addPositionCommand() throws Exception {
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("add, 1, 2"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(1, commandRunner.getCommands().size());

        Command addCommand = commandRunner.getCommands().poll();
        assertEquals(AddPositionCommand.class, addCommand.getClass());

        addCommand.execute();
        assertNotNull(IoC.resolve("order"));
    }

    @Test
    void addPositionCommandFail() throws Exception {
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("put, 1, 2"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(0, commandRunner.getCommands().size());
    }

    @Test
    void deleteOrderCommand() throws Exception {
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("delete, 1"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(1, commandRunner.getCommands().size());
        assertEquals(DeleteOrderCommand.class, commandRunner.getCommands().poll().getClass());
    }

    @Test
    void deleteOrderCommandFail() throws Exception {
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("del, 1"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(0, commandRunner.getCommands().size());
    }

    @Test
    void getMenuCommand() throws Exception {
        config.menu();
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("menu"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(1, commandRunner.getCommands().size());

        Command getMenuCommand = commandRunner.getCommands().poll();
        assertEquals(GetMenuCommand.class, getMenuCommand.getClass());

        getMenuCommand.execute();
        assertDoesNotThrow(() -> new Exception());
    }

    @Test
    void getMenuCommandFail() throws Exception {
        config.menu();
        Command interpret = new InterpretCommand(new SessionStub(), new TextMessage("dishes"));
        interpret.execute();

        CommandRunner commandRunner = IoC.resolve("commandRunner");

        assertDoesNotThrow(() -> new Exception());
        assertEquals(0, commandRunner.getCommands().size());
    }
}

class SessionStub implements WebSocketSession {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public String getAcceptedProtocol() {
        return null;
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {

    }

    @Override
    public int getTextMessageSizeLimit() {
        return 0;
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {

    }

    @Override
    public int getBinaryMessageSizeLimit() {
        return 0;
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        return null;
    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        System.out.println(message.getPayload());
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void close(CloseStatus status) throws IOException {

    }
}