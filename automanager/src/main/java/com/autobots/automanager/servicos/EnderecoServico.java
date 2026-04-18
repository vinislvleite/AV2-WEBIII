package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class EnderecoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Endereco obterPorCliente(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return cliente.getEndereco();
    }

    public Endereco cadastrar(Long clienteId, Endereco endereco) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setEndereco(endereco);

        clienteRepositorio.save(cliente);

        return endereco;
    }

    public Endereco atualizar(Long clienteId, Endereco atualizacao) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Endereco endereco = cliente.getEndereco();

        if (endereco == null) {
            throw new RuntimeException("Cliente não possui endereço");
        }

        endereco.setCidade(atualizacao.getCidade());
        endereco.setEstado(atualizacao.getEstado());
        endereco.setRua(atualizacao.getRua());

        clienteRepositorio.save(cliente);

        return endereco;
    }

    public void deletar(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setEndereco(null);

        clienteRepositorio.save(cliente);
    }
}