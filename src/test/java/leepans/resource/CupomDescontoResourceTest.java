package leepans.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import leepans.model.CupomDesconto;
import leepans.service.ecommerce.CupomDescontoService;

@QuarkusTest
class CupomDescontoResourceTest {

    private static final String BASE = "/cupons-desconto";
    private static final String ADMIN = BASE + "/admin";

    @InjectMock
    CupomDescontoService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void criar_deveRetornar201() {
        when(service.create(any())).thenReturn(cupom(1L, "PROMO10"));

        given()
                .contentType(ContentType.JSON)
                .body(cupomPayload("PROMO10"))
                .when()
                .post(ADMIN)
                .then()
                .statusCode(201)
                .body("codigo", equalTo("PROMO10"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void listarAdmin_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(cupom(1L, "PROMO10")));

        given().accept(ContentType.JSON).when().get(ADMIN).then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorIdAdmin_deveRetornar200() {
        when(service.findById(1L)).thenReturn(cupom(1L, "PROMO10"));

        given().accept(ContentType.JSON).when().get(ADMIN + "/1").then()
                .statusCode(200)
                .body("codigo", equalTo("PROMO10"));
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void buscarPorAtivo_deveRetornar200() {
        when(service.findByAtivo(true)).thenReturn(List.of(cupom(1L, "PROMO10")));

        given().accept(ContentType.JSON).when().get(BASE + "/ativo/true").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void buscarPorCodigo_deveRetornar200() {
        when(service.findByCodigo("PROMO10")).thenReturn(cupom(1L, "PROMO10"));

        given().accept(ContentType.JSON).when().get(BASE + "/codigo/PROMO10").then()
                .statusCode(200)
                .body("codigo", equalTo("PROMO10"));
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void buscarAplicaveis_deveRetornar200() {
        
        when(service.findByAtivoAndValorMinimoCompra(anyBoolean(), any(BigDecimal.class)))
                .thenReturn(List.of(cupom(1L, "PROMO10")));

        given()
                .accept(ContentType.JSON)
                .queryParam("valorCompra", 500.00)
                .when()
                .get(BASE + "/aplicaveis")
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void listarEcommerce_deveRetornar200() {
        when(service.findByAtivo(true)).thenReturn(List.of(cupom(1L, "PROMO10")));

        given().accept(ContentType.JSON).when().get(BASE).then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .body(cupomPayload("PROMO10").replace("}", ",\"version\":1}"))
                .when()
                .put(ADMIN + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    void remover_deveRetornar204() {
        doNothing().when(service).delete(1L);

        given().when().delete(ADMIN + "/1").then().statusCode(204);
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void criar_deveRetornar403ParaCliente() {
        given()
                .contentType(ContentType.JSON)
                .body(cupomPayload("PROMO10"))
                .when()
                .post(ADMIN)
                .then()
                .statusCode(403);
    }

    private static String cupomPayload(String codigo) {
        return """
                {
                  "codigo":"%s",
                  "valorDesconto":10.0,
                  "percentualDesconto":5.0,
                  "valorMinimoCompra":100.0,
                  "dataValidade":"2030-12-31T23:59:59",
                  "quantidadeDisponivel":100,
                  "ativo":true
                }
                """.formatted(codigo);
    }

    private static CupomDesconto cupom(Long id, String codigo) {
        CupomDesconto c = new CupomDesconto();
        c.setId(id);
        c.setCodigo(codigo);
        c.setValorDesconto(BigDecimal.TEN);
        c.setPercentualDesconto(new BigDecimal(5));
        c.setValorMinimoCompra(new BigDecimal(100));
        c.setDataValidade(LocalDateTime.now().plusDays(30));
        c.setQuantidadeDisponivel(100);
        c.setAtivo(true);
        return c;
    }
}
