package leepans.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import leepans.dto.auth.AuthResponseDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.model.Perfil;
import leepans.service.auth.AuthService;
import leepans.service.auth.EmailService;
import leepans.support.TestJwt;

@QuarkusTest
class AuthResourceTest {

    private static final String BASE = "/auth";

    @InjectMock
    AuthService authService;

    @InjectMock
    EmailService emailService;

    @BeforeEach
    void setUp() {
        reset(authService, emailService);
    }

    @Test
    void login_deveRetornar200ComToken() {
        when(authService.login(any())).thenReturn(new AuthResponseDTO("token-jwt", "Bearer"));

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"user\",\"senha\":\"secret\"}")
                .when()
                .post(BASE + "/login")
                .then()
                .statusCode(200)
                .body("token", equalTo("token-jwt"))
                .body("tipo", equalTo("Bearer"));
    }

    @Test
    void login_deveRetornar422QuandoPayloadInvalido() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"\"}")
                .when()
                .post(BASE + "/login")
                .then()
                .statusCode(422);
    }

    @Test
    @TestJwt
    void info_deveRetornar200QuandoAutenticado() {
        when(authService.info(any())).thenReturn(new UsuarioResponseDTO(1L, "cliente@test.com", "Cliente", Perfil.CLIENTE, null));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE + "/info")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("login", equalTo("cliente@test.com"));
    }

    @Test
    void info_deveRetornar401SemAutenticacao() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE + "/info")
                .then()
                .statusCode(401);
    }

    @Test
    @TestJwt
    void alterarSenha_deveRetornar200QuandoValido() {
        when(authService.validarRequisicaoSenha(any())).thenReturn(true);
        when(emailService.sendPasswordEmail("cliente@test.com")).thenReturn("ok");

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"cliente@test.com\",\"senhaAtual\":\"atual\"}")
                .when()
                .post(BASE + "/alterar-senha")
                .then()
                .statusCode(200);
    }

    @Test
    @TestJwt
    void alterarSenha_deveRetornar400QuandoInvalido() {
        when(authService.validarRequisicaoSenha(any())).thenReturn(false);

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"cliente@test.com\",\"senhaAtual\":\"errada\"}")
                .when()
                .post(BASE + "/alterar-senha")
                .then()
                .statusCode(400);
    }

    @Test
    void esqueciSenha_deveRetornar200QuandoValido() {
        when(authService.validarRequisicaoSenha(any())).thenReturn(true);
        when(emailService.sendPasswordEmail("user@test.com")).thenReturn("ok");

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"user@test.com\",\"senhaAtual\":\"atual\"}")
                .when()
                .post(BASE + "/esqueci-senha")
                .then()
                .statusCode(200);

        verify(emailService).sendPasswordEmail("user@test.com");
    }
}
