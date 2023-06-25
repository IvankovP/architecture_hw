package space.battle.authentication.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.battle.authentication.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public int createGame(@RequestBody List<String> userNames) {
        return gameService.createGame(userNames);
    }
}
