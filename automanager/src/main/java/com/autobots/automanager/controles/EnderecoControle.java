package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.servicos.EnderecoServico;

@RestController
@RequestMapping("/cliente/{clienteId}/endereco")
public class EnderecoControle {

    @Autowired
    private EnderecoServico servico;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping
    public ResponseEntity<Endereco> obter(@PathVariable Long clienteId) {
        Endereco endereco = servico.obterPorCliente(clienteId);
        if (endereco != null) {
            endereco.setClienteId(clienteId);
            adicionadorLink.adicionarLink(endereco);
        }
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public ResponseEntity<Endereco> cadastrar(
            @PathVariable Long clienteId,
            @RequestBody Endereco endereco) {

        Endereco novoEndereco = servico.cadastrar(clienteId, endereco);
        novoEndereco.setClienteId(clienteId);
        adicionadorLink.adicionarLink(novoEndereco);
        
        return ResponseEntity.status(201).body(novoEndereco);
    }

    @PutMapping
    public ResponseEntity<Endereco> atualizar(
            @PathVariable Long clienteId,
            @RequestBody Endereco endereco) {

        Endereco enderecoAtualizado = servico.atualizar(clienteId, endereco);
        enderecoAtualizado.setClienteId(clienteId);
        adicionadorLink.adicionarLink(enderecoAtualizado);
        
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Long clienteId) {
        servico.deletar(clienteId);
        return ResponseEntity.noContent().build();
    }
}