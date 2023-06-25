package space.battle.authentication.service;

import java.util.List;

public interface GameService {
    int createGame(List<String> userNames);

    boolean checkUser(int id, String userName);
}
