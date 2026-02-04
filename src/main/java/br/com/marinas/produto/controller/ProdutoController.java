package br.com.marinas.produto.controller;

import br.com.marinas.produto.model.Produto;
import br.com.marinas.produto.repository.ProdutoRepository;
import br.com.marinas.produto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ProdutoController {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvarProduto(@RequestBody Produto produto){
        return produtoService.salvarProduto(produto);
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> buscarProdutoPeloId(@PathVariable("id") String produtoId){
        return produtoService.buscarProdutoPeloId(produtoId)
                .map(produto -> ResponseEntity.ok().body(produto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/produtos")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> buscarTodosProduto(){
        return produtoService.buscarTodosProduto();
    }

    @PutMapping("/produtos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Produto> atualizarProdutoPeloId(@PathVariable("id") String produtoId,
                                      @RequestBody Produto produto){
        return produtoService.atualizarProdutoPeloId(produtoId, produto);
    }

    @DeleteMapping("/produtos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String excluirProdutoPeloId(@PathVariable("id") String produtoId){
        return produtoService.excluirProdutoPeloId(produtoId);
    }


}
