package br.com.consultorio.controller;

import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.service.SecretariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/secretarias")
public class SecretariaController {

    @Autowired
    private SecretariaService secretariaService;

    @GetMapping("/{idSecretaria}")
    public ResponseEntity<Secretaria> findById(
            @PathVariable("idSecretaria") Long idSecretaria
    ){
        return ResponseEntity.ok().body(this.secretariaService.findById(idSecretaria).get());
    }

    @GetMapping
    public ResponseEntity<Page<Secretaria>> listByAllPage(Pageable pageable){
        return ResponseEntity.ok().body(this.secretariaService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Secretaria secretaria){
        try {
            this.secretariaService.insert(secretaria);
            return ResponseEntity.ok().body("Secretaria Cadastrada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idSecretaria}")
    public ResponseEntity<?> update(
            @RequestBody Secretaria secretaria,
            @PathVariable("idSecretaria") Long idSecretaria
    ){
        try {
            this.secretariaService.update(idSecretaria, secretaria);
            return ResponseEntity.ok().body("Secretaria Atualizada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{idSecretaria}")
    public ResponseEntity<?> disable(
            @RequestBody Secretaria secretaria,
            @PathVariable("idSecretaria") Long idSecretaria
    ){
        try {
            this.secretariaService.disable(idSecretaria, secretaria);
            return ResponseEntity.ok().body("Secretaria Desabilitada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
