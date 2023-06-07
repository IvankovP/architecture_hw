package space.battle.core.service;

import space.battle.core.Game;
import space.battle.core.command.Command;

public interface GameService {
    boolean validateToken(String jwt);
    Game getGame(int id);
    void addCommand(Game game, Command command);
}
