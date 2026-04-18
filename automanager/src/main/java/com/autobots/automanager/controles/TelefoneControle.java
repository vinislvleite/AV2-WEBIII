package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.servicos.TelefoneServico;

@RestController
@RequestMapping("/cliente/{clienteId}/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneServico servico;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping
    public ResponseEntity<List<Telefone>> listar(@PathVariable Long clienteId) {
        List<Telefone> telefones = servico.listarPorCliente(clienteId);
        if (!telefones.isEmpty()) {
            telefones.forEach(tel -> tel.setClienteId(clienteId));
            adicionadorLink.adicionarLink(telefones);
        }
        return ResponseEntity.ok(telefones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obter(@PathVariable Long clienteId, @PathVariable Long id) {
        Telefone tel = servico.obterPorId(id);
        if (tel == null || !servico.listarPorCliente(clienteId).contains(tel)) {
            return ResponseEntity.notFound().build();
        }

        tel.setClienteId(clienteId);
        adicionadorLink.adicionarLink(tel);
        return ResponseEntity.ok(tel);
    }

    @PostMapping
    public ResponseEntity<Telefone> cadastrar(@PathVariable Long clienteId, @RequestBody Telefone telefone) {
        Telefone novo = servico.cadastrar(clienteId, telefone);
        novo.setClienteId(clienteId);
        adicionadorLink.adicionarLink(novo);
        return ResponseEntity.status(201).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Telefone> atualizar(@PathVariable Long clienteId, @PathVariable Long id, @RequestBody Telefone telefone) {
        Telefone atualizado = servico.atualizar(id, telefone);
        atualizado.setClienteId(clienteId);
        adicionadorLink.adicionarLink(atualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}