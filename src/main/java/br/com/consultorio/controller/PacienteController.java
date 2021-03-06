package br.com.consultorio.controller;

import br.com.consultorio.entity.Paciente;
import br.com.consultorio.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> findById(
            @PathVariable("idPaciente") Long idPaciente
    ){
        return ResponseEntity.ok().body(this.pacienteService.findById(idPaciente).get());
    }

    @GetMapping
    public ResponseEntity<Page<Paciente>> listByAllPage(Pageable pageable){
        return ResponseEntity.ok().body(this.pacienteService.listAll(pageable));
    }

    @GetMapping("/busca/{name}")
    public ResponseEntity<Page<Paciente>> findByName(
            Pageable pageable,
            @PathVariable("name") String pacienteName
    ){
        return ResponseEntity.ok().body(this.pacienteService.findByName(pacienteName, pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Paciente paciente){
        try {
            this.pacienteService.insert(paciente);
            return ResponseEntity.ok().body("Paciente Cadastrado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idPaciente}")
    public ResponseEntity<?> update(
            @RequestBody Paciente paciente,
            @PathVariable("idPaciente") Long idPaciente
    ){
        try {
            this.pacienteService.update(idPaciente, paciente);
            return ResponseEntity.ok().body("Paciente Atualizado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{idPaciente}")
    public ResponseEntity<?> disable(
            @RequestBody Paciente paciente,
            @PathVariable("idPaciente") Long idPaciente
    ){
        try {
            this.pacienteService.disable(idPaciente, paciente);
            return ResponseEntity.ok().body("Paciente Desabilitado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
