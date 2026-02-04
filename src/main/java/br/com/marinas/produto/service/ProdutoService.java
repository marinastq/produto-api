package br.com.marinas.produto.service;

import br.com.marinas.produto.model.Produto;
import br.com.marinas.produto.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(Produto produto){
        return produtoRepository.salvar(produto);
    }

    public Optional<Produto> buscarProdutoPeloId(String produtoId){
        return produtoRepository.buscarProdutoPeloId(produtoId);
    }

    public List<Produto> buscarTodosProduto(){
        return produtoRepository.buscarTodosProdutos();
    }

    public ResponseEntity<Produto> atualizarProdutoPeloId(String produtoId,
                                          Produto produto){
        //return produtoRepository.atualizarProduto(produtoId, produto);
        return produtoRepository.buscarProdutoPeloId(produtoId)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produto.getNome());
                    produtoExistente.setPreco(produto.getPreco());

                    Produto atualizado = produtoRepository.atualizarProduto(produtoExistente);
                    return ResponseEntity.ok().body(atualizado);
                })
               .orElse(ResponseEntity.notFound().build());
    }

    public String excluirProdutoPeloId(String produtoId){
        return produtoRepository.excluirProdutoPeloId(produtoId);
    }
}
