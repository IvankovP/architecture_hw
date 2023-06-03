package space.battle.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.junit.jupiter.api.Test;
import space.battle.authentication.service.AuthServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthTest {

    private final Algorithm algorithm = Algorithm.HMAC256(AuthServiceImpl.JWT_ISSUER);

    @Test
    void createTokenTest() {
        AuthServiceImpl authService = new AuthServiceImpl();
        String jwt = authService.createToken(0, "Test");

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(AuthServiceImpl.JWT_ISSUER)
                .build();

        DecodedJWT decodedJWT = null;

        try {
            decodedJWT = verifier.verify(jwt);
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(decodedJWT);
        assertEquals(0, decodedJWT.getClaim("gameId").asInt());
        assertEquals("Test", decodedJWT.getClaim("userName").asString());
    }
}
