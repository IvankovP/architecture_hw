package space.battle.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import space.battle.core.service.GameServiceImpl;

public class GameUtils {

    public GameUtils() {
        throw new UnsupportedOperationException();
    }

    private static JWTVerifier verifier = JWT.require(Algorithm.HMAC256(GameServiceImpl.jwtIssuer))
            .withIssuer(GameServiceImpl.jwtIssuer)
            .build();

    public static String getUserName(String jwt) {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return decodedJWT.getClaim("userName").asString();

        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
