package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.AdicionadorLinkCliente;
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.servicos.ClienteServico;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {

    @Autowired
    private ClienteServico servico;

    @Autowired
    private AdicionadorLinkCliente adicionadorLink;

    @Autowired
    private AdicionadorLinkDocumento docAdicionador;

    @Autowired
    private AdicionadorLinkTelefone telAdicionador;

    @Autowired
    private AdicionadorLinkEndereco endAdicionador;

    @GetMapping
    public ResponseEntity<List<Cliente>> obterClientes() {
        List<Cliente> clientes = servico.listarTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Cliente cliente : clientes) {
            adicionarLinksAosFilhos(cliente);
        }

        adicionadorLink.adicionarLink(clientes);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable Long id) {
        try {
            Cliente cliente = servico.obterPorId(id);
            adicionarLinksAosFilhos(cliente);
            adicionadorLink.adicionarLink(cliente);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novo = servico.cadastrar(cliente);
        adicionadorLink.adicionarLink(novo);
        return ResponseEntity.status(201).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente atualizacao) {
        try {
            Cliente atualizado = servico.atualizar(id, atualizacao);
            adicionarLinksAosFilhos(atualizado);
            adicionadorLink.adicionarLink(atualizado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        try {
            servico.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void adicionarLinksAosFilhos(Cliente cliente) {
        Long id = cliente.getId();

        if (cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
            cliente.getDocumentos().forEach(doc -> doc.setClienteId(id));
            docAdicionador.adicionarLink(cliente.getDocumentos());
        }

        if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
            cliente.getTelefones().forEach(tel -> tel.setClienteId(id));
            telAdicionador.adicionarLink(cliente.getTelefones());
        }

        if (cliente.getEndereco() != null) {
            cliente.getEndereco().setClienteId(id);
            endAdicionador.adicionarLink(cliente.getEndereco());
        }
    }
}