package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@Service
public class TelefoneServico {

    @Autowired
    private TelefoneRepositorio repositorio;

    private TelefoneAtualizador atualizador = new TelefoneAtualizador();

    public Telefone obterPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));
    }

    public List<Telefone> listarTodos() {
        return repositorio.findAll();
    }

    public Telefone cadastrar(Telefone telefone) {
        return repositorio.save(telefone);
    }

    public Telefone atualizar(Telefone atualizacao) {
        Telefone telefone = obterPorId(atualizacao.getId());
        atualizador.atualizar(telefone, atualizacao);
        return repositorio.save(telefone);
    }

    public void deletar(Long id) {
        Telefone telefone = obterPorId(id);
        repositorio.delete(telefone);
    }
}