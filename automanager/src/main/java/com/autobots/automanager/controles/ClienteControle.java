package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.servicos.ClienteServico;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {

    @Autowired
    private ClienteServico servico;

    @GetMapping("/{id}")
    public Cliente obterCliente(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @GetMapping
    public List<Cliente> obterClientes() {
        return servico.listarTodos();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarCliente(@RequestBody Cliente cliente) {
        servico.cadastrar(cliente);
        return new ResponseEntity<>("Cliente Cadastrado", HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarCliente(@RequestBody Cliente atualizacao) {
        servico.atualizar(atualizacao);
        return new ResponseEntity<>("Cliente Atualizado", HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirCliente(@RequestBody Cliente exclusao) {
        servico.deletar(exclusao.getId());
        return new ResponseEntity<>("Cliente excluído", HttpStatus.OK);
    }
}