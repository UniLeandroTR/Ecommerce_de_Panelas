package leepans.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import leepans.model.ListaDesejo;
import leepans.model.Usuario;
import leepans.service.ecommerce.ListaDesejoService;
import leepans.support.TestJwt;

@QuarkusTest
class ListaDesejoResourceTest {

    private static final String BASE = "/listas-desejo";

    @InjectMock
    ListaDesejoService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    @TestJwt
    void criar_deveRetornar201() {
        when(service.create(any(), any())).thenReturn(lista(1L));

        given()
                .contentType(ContentType.JSON)
                .body("[1,2]")
                .when()
                .post(BASE)
                .then()
                .statusCode(201)
                .body("id", equalTo(1));
    }

    @Test
    @TestJwt
    void buscarWishList_deveRetornar200() {
        when(service.findWishList(any())).thenReturn(lista(1L));

        given().accept(ContentType.JSON).when().get(BASE).then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorIdAdmin_deveRetornar200() {
        when(service.findById(1L)).thenReturn(lista(1L));

        given().accept(ContentType.JSON).when().get(BASE + "/admin/1").then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorUsuario_deveInvocarServico() {
        when(service.findByUsuarioLogin(any())).thenReturn(lista(1L));

        given().when().get(BASE + "/admin/usuario/cliente@test.com").then().statusCode(200);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        doNothing().when(service).update(anyLong(), any());

        given()
                .contentType(ContentType.JSON)
                .body("{\"idUsuario\":1,\"idPanelas\":[1],\"version\":1}")
                .when()
                .put(BASE + "/admin/1")
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void remover_deveRetornar204() {
        doNothing().when(service).delete(1L);

        given().when().delete(BASE + "/admin/1").then().statusCode(204);
    }

    @Test
    @TestJwt
    void adicionarProduto_deveRetornar204() {
        doNothing().when(service).adicionarProduto(1L, 2L);

        given().contentType(ContentType.JSON).when().post(BASE + "/1/produtos/2").then().statusCode(204);
    }

    @Test
    @TestJwt
    void removerProduto_deveRetornar204() {
        doNothing().when(service).removerProduto(1L, 2L);

        given().when().delete(BASE + "/1/produtos/2").then().statusCode(204);
    }

    @Test
    void criar_deveRetornar401SemAutenticacao() {
        given()
                .contentType(ContentType.JSON)
                .body("[1]")
                .when()
                .post(BASE)
                .then()
                .statusCode(401);
    }

    private static ListaDesejo lista(Long id) {
        ListaDesejo lista = new ListaDesejo();
        lista.setId(id);
        Usuario u = new Usuario();
        u.setId(1L);
        u.setLogin(TestJwt.LOGIN);
        lista.setUsuario(u);
        return lista;
    }
}
