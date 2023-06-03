package space.battle.authentication.service;

public interface AuthService {
    String createToken(int id, String userName);
}
