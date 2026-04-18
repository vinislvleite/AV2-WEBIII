package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class DocumentoServico {

    @Autowired
    private DocumentoRepositorio repositorio;

    private DocumentoAtualizador atualizador = new DocumentoAtualizador();

    public Documento obterPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }

    public List<Documento> listarTodos() {
        return repositorio.findAll();
    }

    public Documento cadastrar(Documento documento) {
        return repositorio.save(documento);
    }

    public Documento atualizar(Documento atualizacao) {
        Documento documento = obterPorId(atualizacao.getId());
        atualizador.atualizar(documento, atualizacao);
        return repositorio.save(documento);
    }

    public void deletar(Long id) {
        Documento documento = obterPorId(id);
        repositorio.delete(documento);
    }
}