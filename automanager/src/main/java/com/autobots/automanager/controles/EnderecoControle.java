package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.servicos.EnderecoServico;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {

    @Autowired
    private EnderecoServico servico;

    @GetMapping("/{id}")
    public Endereco obterEndereco(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @GetMapping
    public List<Endereco> listarEnderecos() {
        return servico.listarTodos();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarEndereco(@RequestBody Endereco endereco) {
        servico.cadastrar(endereco);
        return new ResponseEntity<>("Endereço cadastrado", HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarEndereco(@RequestBody Endereco atualizacao) {
        servico.atualizar(atualizacao);
        return new ResponseEntity<>("Endereço atualizado", HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirEndereco(@RequestBody Endereco exclusao) {
        servico.deletar(exclusao.getId());
        return new ResponseEntity<>("Endereço excluído", HttpStatus.OK);
    }
}