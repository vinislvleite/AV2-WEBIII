package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class DocumentoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private DocumentoRepositorio repositorio;

    public Documento obterPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }

    public List<Documento> listarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return cliente.getDocumentos();
    }

    public Documento cadastrar(Long clienteId, Documento documento) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.getDocumentos().add(documento);
        clienteRepositorio.save(cliente);
        return documento;
    }

    public Documento atualizar(Long id, Documento atualizacao) {
        Documento documento = obterPorId(id);
        documento.setNumero(atualizacao.getNumero());
        documento.setTipo(atualizacao.getTipo());
        return repositorio.save(documento);
    }

    public void deletar(Long id) {
        Documento documento = obterPorId(id);
        repositorio.delete(documento);
    }
}