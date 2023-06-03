package space.battle.authentication.controller;

import org.springframework.web.bind.annotation.*;
import space.battle.authentication.service.AuthService;
import space.battle.authentication.service.GameService;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/token")
public class AuthController {

    private final AuthService authService;
    private final GameService gameService;

    public AuthController(AuthService authService, GameService gameService) {
        this.authService = authService;
        this.gameService = gameService;
    }

    @GetMapping
    public String getToken(@RequestParam int id, @RequestParam String userName) {
        if (!gameService.checkUser(id, userName)) {
            throw new InvalidParameterException("Invalid param ID or USERNAME");
        }
        return authService.createToken(id, userName);
    }
}
