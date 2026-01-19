package br.com.marinas.produto.controller;

import br.com.marinas.produto.model.Produto;
import br.com.marinas.produto.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/produtos")
    public Produto salvarProduto(@RequestBody Produto produto){
        return produtoRepository.salvar(produto);
    }

    @GetMapping("/produtos/{id}")
    public Produto buscarProdutoPeloId(@PathVariable("id") String produtoId){
        return produtoRepository.buscarProdutoPeloId(produtoId);
    }

    @GetMapping("/produtos")
    public List<Produto> buscarTodosProduto(){
        return produtoRepository.buscarTodosProdutos();
    }

    @PutMapping("/produtos/{id}")
    public Produto atualizarProdutoPeloId(@PathVariable("id") String produtoId,
                                      @RequestBody Produto produto){
        return produtoRepository.atualizarProduto(produtoId, produto);
    }

    @DeleteMapping("/produtos/{id}")
    public String excluirProdutoPeloId(@PathVariable("id") String produtoId){
        return produtoRepository.excluirProdutoPeloId(produtoId);
    }


}
