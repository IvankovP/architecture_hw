package space.battle.client.service;

import java.io.IOException;
import java.util.Map;

public interface MessageService {
    void send(Map<String, String> message) throws IOException;
}
