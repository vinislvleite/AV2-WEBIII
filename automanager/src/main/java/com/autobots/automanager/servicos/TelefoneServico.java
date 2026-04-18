package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@Service
public class TelefoneServico {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Telefone obterPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));
    }

    public List<Telefone> listarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return cliente.getTelefones();
    }

    public Telefone cadastrar(Long clienteId, Telefone telefone) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.getTelefones().add(telefone);
        clienteRepositorio.save(cliente);

        return telefone;
    }

    public Telefone atualizar(Long id, Telefone atualizacao) {
        Telefone telefone = obterPorId(id);

        telefone.setDdd(atualizacao.getDdd());
        telefone.setNumero(atualizacao.getNumero());

        return repositorio.save(telefone);
    }

    public void deletar(Long id) {
        Telefone telefone = obterPorId(id);
        repositorio.delete(telefone);
    }
}