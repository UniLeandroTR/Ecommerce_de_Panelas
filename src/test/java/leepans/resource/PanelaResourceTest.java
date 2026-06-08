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
import leepans.dto.panela.PanelaEcommerceDTO;
import leepans.dto.panela.PanelaResponseDTO;
import leepans.mapper.CategoriaMapper;
import leepans.mapper.PanelaMapper;
import leepans.model.Categoria;
import leepans.model.Colecao;
import leepans.model.Cor;
import leepans.model.Fornecedor;
import leepans.model.Fundo;
import leepans.model.Material;
import leepans.model.Panela;
import leepans.model.Sustentacao;
import leepans.model.Tamanho;
import leepans.model.Tampa;
import leepans.service.ecommerce.PanelaService;

@QuarkusTest
class PanelaResourceTest {

    private static final String BASE = "/panelas";
    private static final String ADMIN = BASE + "/admin";

    @InjectMock
    PanelaService service;

    @InjectMock
    PanelaMapper mapper;

    @BeforeEach
    void setUp() {
        reset(service, mapper);
        when(mapper.toEntity(any())).thenAnswer(invocation -> panela(1L));
        when(mapper.toEcommerceDTO(any())).thenAnswer(invocation -> {
            Panela panela = invocation.getArgument(0);
            return new PanelaEcommerceDTO(
                    panela.getId(),
                    panela.getModelo(),
                    panela.getPreco(),
                    panela.getCapacidadeLitros(),
                    panela.getIsInducao(),
                    panela.getTamanho(),
                    null,
                    null,
                    CategoriaMapper.toEcommerceDTO(panela.getCategoria()),
                    null,
                    null,
                    null,
                    null,
                    panela.getVersion());
        });
        when(mapper.toResponseDTO(any())).thenAnswer(invocation -> {
            Panela panela = invocation.getArgument(0);
            return new PanelaResponseDTO(
                    panela.getId(),
                    panela.getModelo(),
                    panela.getDataCadastro(),
                    panela.getPreco(),
                    panela.getPeso(),
                    panela.getCapacidadeLitros(),
                    panela.getDescricaco(),
                    panela.getIsInducao(),
                    panela.getTamanho(),
                    CategoriaMapper.toResponse(panela.getCategoria()),
                    null,
                    null,
                    null,
                    null,
                    null,
                    panela.getVersion());
        });
    }

    @Test
    void listarEcommerce_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(panela(1L)));

        given().accept(ContentType.JSON).when().get(BASE).then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].modelo", equalTo("Panela A"));
    }

    @Test
    @TestSecurity(user = "cliente", roles = "CLIENTE")
    void buscarPorIdEcommerce_deveRetornar200() {
        when(service.findById(1L)).thenReturn(panela(1L));

        given().accept(ContentType.JSON).when().get(BASE + "/1").then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void buscarPorIdEcommerce_deveRetornar401SemAutenticacao() {
        given().accept(ContentType.JSON).when().get(BASE + "/1").then().statusCode(401);
    }

    @Test
    void buscarPorCategoria_deveRetornar200() {
        when(service.findByCategoria(1L)).thenReturn(List.of(panela(1L)));

        given().accept(ContentType.JSON).when().get(BASE + "/categorias/1").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void buscarPorColecao_deveRetornar200() {
        when(service.findByColecao(1L)).thenReturn(List.of(panela(1L)));

        given().accept(ContentType.JSON).when().get(BASE + "/colecoes/1").then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void listarAdmin_deveRetornar200() {
        when(service.findAll()).thenReturn(List.of(panela(1L)));

        given().accept(ContentType.JSON).when().get(ADMIN).then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].modelo", equalTo("Panela A"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorIdAdmin_deveRetornar200() {
        when(service.findById(1L)).thenReturn(panela(1L));

        given().accept(ContentType.JSON).when().get(ADMIN + "/1").then()
                .statusCode(200)
                .body("modelo", equalTo("Panela A"));
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void buscarPorIdAdmin_deveRetornar404() {
        when(service.findById(99L)).thenThrow(new NotFoundException());

        given().accept(ContentType.JSON).when().get(ADMIN + "/99").then().statusCode(404);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void criar_deveRetornar201() {
        Panela criada = panela(10L);
        when(service.create(any())).thenReturn(criada);

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "modelo":"Panela Nova",
                          "preco":150,
                          "peso":2.0,
                          "capacidadeLitros":3.0,
                          "descricao":"Desc",
                          "isInducao":true,
                          "tamanho":"PEQUENA",
                          "idColecao":1,
                          "idCor":1,
                          "idMaterialPrincipal":1,
                          "idCategoria":1,
                          "idFornecedor":1,
                          "idFundo":1,
                          "idSustentacao":1
                        }
                        """)
                .when()
                .post(ADMIN)
                .then()
                .statusCode(201);
    }

    @Test
    @TestSecurity(user = "func", roles = "FUNCIONARIO")
    void atualizar_deveRetornar204() {
        doNothing().when(service).update(any(Long.class), any());

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "modelo":"Panela Atualizada",
                          "preco":200,
                          "peso":2.5,
                          "capacidadeLitros":4.0,
                          "descricao":"Desc",
                          "isInducao":false,
                          "tamanho":"MEDIA",
                          "idColecao":1,
                          "idCor":1,
                          "idMaterialPrincipal":1,
                          "idCategoria":1,
                          "idFornecedor":1,
                          "idFundo":1,
                          "idSustentacao":1,
                          "version":1
                        }
                        """)
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
    void remover_deveRetornar403ParaCliente() {
        given().when().delete(ADMIN + "/1").then().statusCode(403);
    }

    private static Panela panela(Long id) {
        Panela p = new Panela();
        p.setId(id);
        p.setModelo("Panela A");
        p.setPreco(100.0);
        p.setCapacidadeLitros(2.0);
        p.setIsInducao(true);
        p.setTamanho(Tamanho.PEQUENA);

        Categoria cat = new Categoria();
        cat.setId(1L);
        cat.setTipo("Panelas");
        p.setCategoria(cat);

        Colecao col = new Colecao();
        col.setId(1L);
        col.setNome("Premium");
        p.setColecao(col);

        Fornecedor f = new Fornecedor();
        f.setId(1L);
        f.setNome("Forn");
        p.setFornecedor(f);

        Cor cor = new Cor();
        cor.setId(1L);
        cor.setNome("Vermelho");
        p.setCor(cor);

        Material mat = new Material();
        mat.setId(1L);
        mat.setNome("Aço");
        p.setMaterialPrincipal(mat);

        Tampa t = new Tampa();
        t.setId(1L);
        p.setTampa(t);

        Fundo fu = new Fundo();
        fu.setId(1L);
        p.setFundo(fu);

        Sustentacao s = new Sustentacao();
        s.setId(1L);
        p.setSustentacao(s);

        return p;
    }
}
