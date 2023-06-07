package space.battle.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    public static final String JWT_ISSUER = "auth-service";
    private final Algorithm algorithm = Algorithm.HMAC256(JWT_ISSUER);

    @Override
    public String createToken(int id, String userName) {
        return JWT.create()
                .withIssuer(JWT_ISSUER)
                .withSubject(JWT_ISSUER)
                .withClaim("gameId", id)
                .withClaim("userName", userName)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400L))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date())
                .sign(algorithm);
    }
}
