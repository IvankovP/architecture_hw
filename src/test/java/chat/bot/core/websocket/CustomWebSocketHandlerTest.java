package chat.bot.core.websocket;

import chat.bot.core.config.Config;
import chat.bot.core.service.OrderServiceImpl;
import chat.bot.core.utils.CommandRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import javax.websocket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomWebSocketHandlerTest {

    private final Config config = new Config();
    private CustomWebSocketHandler handler;
    private CommandRunner commandRunner;

    @BeforeEach
    void setUp() {
        commandRunner = config.commandRunner();
        handler = new CustomWebSocketHandler(new OrderServiceImpl(null), commandRunner);
    }

    @Test
    void handleTextMessage() {
        handler.handleTextMessage(new SessionStub(), new TextMessage("create"));
        assertEquals(1, commandRunner.getCommands().size());
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