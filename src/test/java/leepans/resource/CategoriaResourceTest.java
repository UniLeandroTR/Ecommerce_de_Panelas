package leepans.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import leepans.model.Categoria;
import leepans.service.ecommerce.CategoriaService;

@QuarkusTest
class CategoriaResourceTest {

    private static final String ADMIN = "/categorias/admin";

    @InjectMock
    CategoriaService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void listarAdmin_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(categoria(1L, "Panelas")));

        given().accept(ContentType.JSON).when().get(ADMIN).then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].tipo", equalTo("Panelas"));
    }

    @Test
    void listarAdmin_deveRetornar401SemAutenticacao() {
        given().accept(ContentType.JSON).when().get(ADMIN).then().statusCode(401);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorId_deveRetornar200() {
        when(service.findById(1L)).thenReturn(categoria(1L, "Panelas"));

        given().accept(ContentType.JSON).when().get(ADMIN + "/1").then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("tipo", equalTo("Panelas"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorId_deveRetornar404QuandoInexistente() {
        when(service.findById(99L)).thenThrow(new NotFoundException());

        given().accept(ContentType.JSON).when().get(ADMIN + "/99").then().statusCode(404);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorNome_deveRetornar200() {
        when(service.findByNome("Panelas")).thenReturn(List.of(categoria(1L, "Panelas")));

        given().accept(ContentType.JSON).when().get(ADMIN + "/nome/Panelas").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void criar_deveRetornar201() {
        when(service.create(any(Categoria.class))).thenReturn(categoria(10L, "Utensilios"));

        given()
                .contentType(ContentType.JSON)
                .body("{\"tipo\":\"Utensilios\"}")
                .when()
                .post(ADMIN)
                .then()
                .statusCode(201)
                .body("id", equalTo(10));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .body("{\"tipo\":\"Nova\",\"version\":1}")
                .when()
                .put(ADMIN + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar422SemVersion() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"tipo\":\"Nova\"}")
                .when()
                .put(ADMIN + "/1")
                .then()
                .statusCode(422);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void remover_deveRetornar204() {
        doNothing().when(service).delete(1L);

        given().when().delete(ADMIN + "/1").then().statusCode(204);
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void remover_deveRetornar403ParaCliente() {
        given().when().delete(ADMIN + "/1").then().statusCode(403);
    }

    private static Categoria categoria(Long id, String tipo) {
        Categoria c = new Categoria();
        c.setId(id);
        c.setTipo(tipo);
        return c;
    }
}
