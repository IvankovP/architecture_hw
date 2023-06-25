package space.battle.authentication.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameServiceImpl implements GameService {

    private final Map<Integer, List<String>> games = new HashMap<>();
    private final AtomicInteger lastGameId = new AtomicInteger();

    @Override
    public int createGame(List<String> userNames) {
        int gameId = lastGameId.getAndIncrement();
        games.putIfAbsent(gameId, userNames);
        return gameId;
    }

    @Override
    public boolean checkUser(int id, String userName) {
        return games.getOrDefault(id, Collections.emptyList()).contains(userName);
    }
}
