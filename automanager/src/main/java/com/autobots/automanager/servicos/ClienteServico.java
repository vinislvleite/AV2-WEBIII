package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class ClienteServico {

    @Autowired
    private ClienteRepositorio repositorio;

    private ClienteAtualizador atualizador = new ClienteAtualizador();

    public Cliente obterPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Cliente> listarTodos() {
        return repositorio.findAll();
    }

    public Cliente cadastrar(Cliente cliente) {
        return repositorio.save(cliente);
    }

    public Cliente atualizar(Cliente atualizacao) {
        Cliente cliente = obterPorId(atualizacao.getId());
        atualizador.atualizar(cliente, atualizacao);
        return repositorio.save(cliente);
    }

    public void deletar(Long id) {
        Cliente cliente = obterPorId(id);
        repositorio.delete(cliente);
    }
}