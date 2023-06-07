package space.battle.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Service;
import space.battle.core.Game;
import space.battle.core.command.Command;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final String jwtIssuer = "auth-service";
    private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtIssuer))
            .withIssuer(jwtIssuer)
            .build();

    private final List<Game> games;

    public GameServiceImpl(List<Game> games) {
        this.games = games;
    }

    @Override
    public boolean validateToken(String jwt) {
        if (jwt == null || jwt.isBlank()) {
            return false;
        }
        
        try {
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return jwtIssuer.equalsIgnoreCase(decodedJWT.getIssuer());

        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Game getGame(int id) {
        return games.stream()
                .filter(game -> game.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addCommand(Game game, Command command) {
        game.getCommands().offer(command);
    }
}
