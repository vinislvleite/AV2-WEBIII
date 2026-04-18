package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@Service
public class EnderecoServico {

    @Autowired
    private EnderecoRepositorio repositorio;

    private EnderecoAtualizador atualizador = new EnderecoAtualizador();

    public Endereco obterPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereco não encontrado"));
    }

    public List<Endereco> listarTodos() {
        return repositorio.findAll();
    }

    public Endereco cadastrar(Endereco endereco) {
        return repositorio.save(endereco);
    }

    public Endereco atualizar(Endereco atualizacao) {
        Endereco endereco = obterPorId(atualizacao.getId());
        atualizador.atualizar(endereco, atualizacao);
        return repositorio.save(endereco);
    }

    public void deletar(Long id) {
        Endereco endereco = obterPorId(id);
        repositorio.delete(endereco);
    }
}