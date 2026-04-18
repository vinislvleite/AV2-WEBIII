package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.servicos.DocumentoServico;

@RestController
@RequestMapping("/cliente/{clienteId}/documento")
public class DocumentoControle {

    @Autowired
    private DocumentoServico servico;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping
    public ResponseEntity<List<Documento>> listar(@PathVariable Long clienteId) {
        List<Documento> documentos = servico.listarPorCliente(clienteId);
        if (!documentos.isEmpty()) {
            for (Documento doc : documentos) {
                doc.setClienteId(clienteId);
            }
            adicionadorLink.adicionarLink(documentos);
        }
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obter(
            @PathVariable Long clienteId,
            @PathVariable Long id) {

        Documento doc = servico.obterPorId(id);

        if (doc == null || !servico.listarPorCliente(clienteId).contains(doc)) {
            return ResponseEntity.notFound().build();
        }

        doc.setClienteId(clienteId);
        adicionadorLink.adicionarLink(doc);
        return ResponseEntity.ok(doc);
    }

    @PostMapping
    public ResponseEntity<Documento> cadastrar(@PathVariable Long clienteId, @RequestBody Documento documento) {
        Documento salvo = servico.cadastrar(clienteId, documento);
        salvo.setClienteId(clienteId);
        adicionadorLink.adicionarLink(salvo);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizar(
            @PathVariable Long clienteId,
            @PathVariable Long id,
            @RequestBody Documento documento) {

        Documento docAtualizado = servico.atualizar(id, documento);
        docAtualizado.setClienteId(clienteId);
        adicionadorLink.adicionarLink(docAtualizado);
        
        return ResponseEntity.ok(docAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}