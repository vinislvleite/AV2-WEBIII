package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.servicos.TelefoneServico;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneServico servico;

    @GetMapping("/{id}")
    public Telefone obterTelefone(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @GetMapping
    public List<Telefone> listarTelefones() {
        return servico.listarTodos();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarTelefone(@RequestBody Telefone telefone) {
        servico.cadastrar(telefone);
        return new ResponseEntity<>("Telefone cadastrado", HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarTelefone(@RequestBody Telefone atualizacao) {
        servico.atualizar(atualizacao);
        return new ResponseEntity<>("Telefone atualizado", HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirTelefone(@RequestBody Telefone exclusao) {
        servico.deletar(exclusao.getId());
        return new ResponseEntity<>("Telefone excluído", HttpStatus.OK);
    }
}