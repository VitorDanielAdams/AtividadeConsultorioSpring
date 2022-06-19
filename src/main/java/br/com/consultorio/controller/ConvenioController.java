package br.com.consultorio.controller;

import br.com.consultorio.entity.Convenio;
import br.com.consultorio.service.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/convenios")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    @GetMapping("/{idConvenio}")
    public ResponseEntity<Convenio> findById(
            @PathVariable("idConvenio") Long idConvenio
    ){
        return ResponseEntity.ok().body(this.convenioService.findById(idConvenio).get());
    }

    @GetMapping
    public ResponseEntity<Page<Convenio>> listByAllPage(Pageable pageable){
        return ResponseEntity.ok().body(this.convenioService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Convenio convenio){
        try {
            this.convenioService.insert(convenio);
            return ResponseEntity.ok().body("O Convênio Foi Cadastrado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idConvenio}")
    public ResponseEntity<?> update(
            @RequestBody Convenio convenio,
            @PathVariable("idConvenio") Long idConvenio
    ){
        try {
            this.convenioService.update(idConvenio, convenio);
            return ResponseEntity.ok().body("O Convênio Foi Atualizado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{idConvenio}")
    public ResponseEntity<?> disable(
            @RequestBody Convenio convenio,
            @PathVariable("idConvenio") Long idConvenio
    ){
        try {
            this.convenioService.disable(idConvenio, convenio);
            return ResponseEntity.ok().body("O Convênio Foi Desabilitado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
