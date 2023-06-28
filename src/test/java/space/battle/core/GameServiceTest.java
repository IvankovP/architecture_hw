package space.battle.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.authentication.service.AuthServiceImpl;
import space.battle.core.adapter.FuelAdapter;
import space.battle.core.adapter.MoveAdapter;
import space.battle.core.command.Command;
import space.battle.core.command.action.CheckFuelCommand;
import space.battle.core.command.action.MoveCommand;
import space.battle.core.entity.Ship;
import space.battle.core.exception.CommandExceptionHandler;
import space.battle.core.service.GameService;
import space.battle.core.service.GameServiceImpl;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl(getGames());
    }

    private List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        games.add(createGame(1));
        games.add(createGame(2));
        games.add(createGame(3));
        return games;
    }

    private Game createGame(int id) {
        LinkedBlockingQueue<Command> commands = new LinkedBlockingQueue<>();
        return new Game(commands, new CommandExceptionHandler(commands, new HashMap<>()), id, Collections.emptyList());
    }

    @Test
    void validateTokenTest() {
        AuthServiceImpl authService = new AuthServiceImpl();
        String jwt = authService.createToken(0, "Test");

        assertTrue(gameService.validateToken(jwt));
    }

    @Test
    void getGameTest() {
        assertNotNull(gameService.getGame(2));
    }

    @Test
    void getGameNotFoundTest() {
        assertNull(gameService.getGame(5));
    }

    @Test
    void addCommandTest() {
        Game game = gameService.getGame(1);

        MoveCommand moveCommand = new MoveCommand(new MoveAdapter(new Ship()));
        gameService.addCommand(game, moveCommand);

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(new FuelAdapter(new Ship()));
        gameService.addCommand(game, checkFuelCommand);

        assertEquals(2, game.getCommands().size());
    }
}
