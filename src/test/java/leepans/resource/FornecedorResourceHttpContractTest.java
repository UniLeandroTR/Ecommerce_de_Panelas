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
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;
import leepans.model.Fornecedor;
import leepans.service.FornecedorService;

@QuarkusTest
public class FornecedorResourceHttpContractTest {

    private static final String BASE_URL = "/fornecedores";

    @InjectMock
    FornecedorService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void deveListarFornecedoresComStatus200() {
        when(service.findAll()).thenReturn(List.of(
                fornecedor(1L, "Forn A", "63999999999", "12345678912345"),
                fornecedor(2L, "Forn B", "63888888888", "98745632114785")));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Forn A"))
                .body("[0].telefone", equalTo("63999999999"))
                .body("[0].cnpj", equalTo("12345678912345"));

    }

    @Test
    void deveBuscarPorIdComStatus200() {
        when(service.findById(1L)).thenReturn(fornecedor(1L, "Forn A", "63999999999", "12345678912345"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("nome", equalTo("Forn A"))
                .body("telefone", equalTo("63999999999"))
                .body("cnpj", equalTo("12345678912345"));
    }

    @Test
    void deveRetornar404QuandoBuscarPorIdInexistente() {
        when(service.findById(999L)).thenThrow(new NotFoundException("Fornecedor nao encontrado"));

        given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }

    @Test
    void deveCriarFornecedorComStatus201() {
        when(service.create(any(Fornecedor.class)))
                .thenReturn(fornecedor(10L, "Forn X", "63555555555", "14785236985246"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Forn X\",\"telefone\":\"63555555555\",\"cnpj\":\"14785236985246\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("nome", equalTo("Forn X"))
                .body("telefone", equalTo("63555555555"))
                .body("cnpj", equalTo("14785236985246"));
    }

    @Test
    void deveAtualizarFornecedorComStatus204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"nome\":\"Forn Novo\",\"telefone\":\"63555555555\",\"cnpj\":\"12345678901234\"}")
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRemoverFornecedorComStatus204() {
        doNothing().when(service).delete(1L);

        given()
                .accept(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

    private Fornecedor fornecedor(Long id, String nome, String telefone, String cnpj) {
        Fornecedor f = new Fornecedor();
        f.setId(id);
        f.setNome(nome);
        f.setTelefone(telefone);
        f.setCnpj(cnpj);
        return f;
    }
}
