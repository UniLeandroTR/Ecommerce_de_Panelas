package leepans.service.auth;

import java.security.SecureRandom;
import java.time.Duration;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CacheService {

    private final ValueCommands<String, String> valueCommands;
    private final KeyCommands<String> keyCommands;

    public CacheService(RedisDataSource ds){
        this.valueCommands = ds.value(String.class);
        this.keyCommands = ds.key();
    }

    public String getTokenSenha(String login) {
        String token = String.format("%06d", new SecureRandom().nextInt(1000000));
        valueCommands.set(token, login);
        keyCommands.expire(token, Duration.ofMinutes(5));
        return token;
    }

    public boolean checkToken(String login, String token){
        if(valueCommands.get(token).equals(login))
            return true;

        return false;
    }

    public void invalidateToken(String token){
        keyCommands.del(token);
    }
}
