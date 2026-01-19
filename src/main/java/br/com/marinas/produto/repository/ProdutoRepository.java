package br.com.marinas.produto.repository;

import br.com.marinas.produto.configuration.DynamoDbConfig;
import br.com.marinas.produto.model.Produto;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    private DynamoDBMapper dynamoDBMapper;

    public ProdutoRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Produto salvar(Produto produto){
        dynamoDBMapper.save(produto);
        return produto;
    }

    public Produto buscarProdutoPeloId(String produtoId){
        return dynamoDBMapper.load(Produto.class, produtoId);
    }

    public List<Produto> buscarTodosProdutos(){
        return dynamoDBMapper.scan(Produto.class, new DynamoDBScanExpression());
    }

    public String excluirProdutoPeloId(String produtoId){
        Produto produto = dynamoDBMapper.load(Produto.class, produtoId);
        dynamoDBMapper.delete(produto);
        return "Produto excluído com sucesso";
    }

    public Produto atualizarProduto(String produtoId, Produto produto){
        dynamoDBMapper.save(
                new Produto(
                        produtoId,
                        produto.getNome(),
                        produto.getPreco()),
                new DynamoDBSaveExpression()
                        .withExpectedEntry("produtoId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(produtoId)))
        );
        return produto;
    }

}
