package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.servicos.DocumentoServico;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

    @Autowired
    private DocumentoServico servico;

    @GetMapping("/{id}")
    public Documento obterDocumento(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @GetMapping
    public List<Documento> listarDocumentos() {
        return servico.listarTodos();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarDocumento(@RequestBody Documento documento) {
        servico.cadastrar(documento);
        return new ResponseEntity<>("Documento cadastrado", HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarDocumento(@RequestBody Documento documento) {
        servico.atualizar(documento);
        return new ResponseEntity<>("Documento atualizado", HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirDocumento(@RequestBody Documento documento) {
        servico.deletar(documento.getId());
        return new ResponseEntity<>("Documento excluído", HttpStatus.OK);
    }
}