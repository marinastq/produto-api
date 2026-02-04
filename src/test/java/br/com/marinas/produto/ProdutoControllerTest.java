package br.com.marinas.produto;

import br.com.marinas.produto.model.Produto;
import br.com.marinas.produto.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProdutoRepository produtoRepository;

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DisplayName("deve criar um produto com sucesso")
    @Test
    public void deveCriarProdutoComSucesso() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/produtos")
                .content(asJsonString(new Produto(
                        UUID.randomUUID().toString(),
                        "java",
                        79.00
                )))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @DisplayName("deve buscar produto pelo ID com sucesso")
    @Test
    public void deveBuscarProdutoPeloIdComSucesso() throws  Exception{
        Produto produto = new Produto(
                "123",
                "IA",
                100.00);

        Mockito.when(produtoRepository.buscarProdutoPeloId("123"))
                .thenReturn(Optional.of(produto));

        this.mockMvc.perform(get("/v1/produtos/{id}", "123"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("deve buscar produto pelo ID nao existente")
    @Test
    public void deveBuscarProdutoPeloIdNaoExistente() throws  Exception{
        this.mockMvc.perform(get("/v1/produtos/{id}", "123"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


}
