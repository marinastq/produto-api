package br.com.marinas.produto;

import br.com.marinas.produto.model.Produto;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;


public class RestAssuredTest {

    static{
        RestAssured.baseURI = "http://localhost:8080/";
    }

    public Response criarProduto(Produto produto) throws Exception {
        RequestSpecification requestSpecification =
                given()
                        .contentType("application/json")
                        .body(produto);
        return requestSpecification.post("/v1/produtos");
    }

    private ResponseSpecification responseSpecification(int responseStatus){
        return new ResponseSpecBuilder()
                .expectStatusCode(responseStatus)
                .build();
    }

    @Test
    @DisplayName("Deve criar um produto com sucesso")
    public void deveCriarUmProdutoComSucesso(){
        try{
            criarProduto(new Produto(
                    "123",
                    "java",
                    100
            ))
                    .then()
                    .assertThat().spec(responseSpecification(201))
                    .and()
                    .assertThat().body("nome", equalTo("java"))
            ;
        }catch (Exception e){
            fail("nao foi possivel cadastrar o produto");
        }
    }

    @Test
    @DisplayName("Deve buscar um produto pelo id com sucesso")
    public void deveBuscarUmProdutoPeloIdComSucesso(){
        try{
            criarProduto(new Produto(
                    "123",
                    "java",
                    100.0
            ));
            given()
                    .when()
                    .basePath("/v1/produtos")
                    .get("/123")
                    .then()
                    .assertThat().spec(responseSpecification(200))
                    .and()
                    .assertThat().body("preco", equalTo("100.0"));
        }catch (Exception e){
            fail("nao foi possivel buscar o produto");
        }
    }

    @Test
    @DisplayName("Deve excluir um produto pelo id com sucesso")
    public void deveExcluirUmProdutoPeloIdComSucesso(){
        try{
            criarProduto(new Produto(
                    "123",
                    "java",
                    100.0
            ));
            given()
                    .when()
                    .basePath("/v1/produtos")
                    .delete("/123")
                    .then()
                    .assertThat().spec(responseSpecification(204))
                    ;
        }catch (Exception e){
            fail("nao foi possivel excluir o produto");
        }
    }

    @Test
    @DisplayName("Deve retonrar erro ao excluir um produto que nao existe")
    public void deveRetornarErroAoExcluirUmProdutoQueNaoExiste(){
        try{
            given()
                    .when()
                    .basePath("/v1/produtos")
                    .delete("/123")
                    .then()
                    .assertThat().spec(responseSpecification(500))
            ;
        }catch (Exception e){
            fail("nao foi possivel excluir o produto");
        }
    }
}
