package space.battle.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.battle.authentication.service.GameServiceImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl();
        gameService.createGame(Arrays.asList("Test", "Test2"));
    }

    @Test
    void createGameTest() {
        int gameId = gameService.createGame(Arrays.asList("Test", "Test2"));
        assertEquals(1, gameId);
    }

    @Test
    void checkUserTest() {
        boolean result = gameService.checkUser(0, "Test");
        assertTrue(result);
    }

    @Test
    void checkUserNotFoundTest() {
        boolean result = gameService.checkUser(0, "Test5");
        assertFalse(result);
    }
}
