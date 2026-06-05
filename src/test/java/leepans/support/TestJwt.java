package leepans.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@TestSecurity(user = "usuario-teste", roles = { "CLIENTE" })
@JwtSecurity(claims = @Claim(key = "upn", value = TestJwt.LOGIN))
public @interface TestJwt {

    String LOGIN = "cliente@test.com";
}
