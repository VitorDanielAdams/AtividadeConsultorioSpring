package br.com.consultorio.controller;

import br.com.consultorio.entity.Especialidade;
import br.com.consultorio.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping("/{idEspecialidade}")
    public ResponseEntity<Especialidade> findById(
            @PathVariable("idEspecialidade") Long idEspecialidade
    ){
        return ResponseEntity.ok().body(this.especialidadeService.findById(idEspecialidade).get());
    }

    @GetMapping
    public ResponseEntity<Page<Especialidade>> listByAllPage(Pageable pageable){
        return ResponseEntity.ok().body(this.especialidadeService.listAll(pageable));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> insert(@RequestBody Especialidade especialidade){
        try {
            this.especialidadeService.insert(especialidade);
            return ResponseEntity.ok().body("Especialidade Cadastrada Com Sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idEspecialidade}")
    public ResponseEntity<?> update(
            @RequestBody Especialidade especialidade,
            @PathVariable("idEspecialidade") Long idEspecialidade
    ){
        try {
            this.especialidadeService.update(idEspecialidade,especialidade);
            return ResponseEntity.ok().body("Especialidade Atualizado Com Sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{idEspecialidade}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Especialidade especialidade,
            @PathVariable("idEspecialidade") Long idEspecialidade
    ){
        try {
            this.especialidadeService.disable(idEspecialidade,especialidade);
            return ResponseEntity.ok().body("Especialidade Desabilitado Com Sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
