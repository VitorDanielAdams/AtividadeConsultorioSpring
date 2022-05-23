package br.com.consultorio.controller;

import br.com.consultorio.entity.Agenda;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/{idAgenda}")
    public ResponseEntity<Agenda> findById(
            @PathVariable("idAgenda") Long idAgenda
    ){
        return ResponseEntity.ok().body(this.agendaService.findById(idAgenda).get());
    }

    @GetMapping
    public ResponseEntity<Page<Agenda>> listByAllPage(Pageable pageable){
        return ResponseEntity.ok().body(this.agendaService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Agenda agenda,
                                    @RequestBody Secretaria secretaria,
                                    @RequestBody String observacao){
        try {
            this.agendaService.insert(agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Agenda Cadastrada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idAgenda}")
    public ResponseEntity<?> update(
            @RequestBody Agenda agenda,
            @RequestBody Secretaria secretaria,
            @RequestBody String observacao,
            @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.update(idAgenda,agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Agenda Atualizada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/aprovado/{idAgenda}")
    public ResponseEntity<?> updateStatusAprovado(
        @RequestBody Agenda agenda,
        @RequestBody Secretaria secretaria,
        @RequestBody String observacao,
        @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.updateStatusAprovado(idAgenda,agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Agenda Aprovada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/rejeitado/{idAgenda}")
    public ResponseEntity<?> updateStatusRejeitado(
            @RequestBody Agenda agenda,
            @RequestBody Secretaria secretaria,
            @RequestBody String observacao,
            @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.updateStatusRejeitado(idAgenda,agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Agenda Rejeitada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/compareceu/{idAgenda}")
    public ResponseEntity<?> updateStatusCompareceu(
            @RequestBody Agenda agenda,
            @RequestBody Secretaria secretaria,
            @RequestBody String observacao,
            @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.updateStatusCompareceu(idAgenda,agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Status Da Agenda Alterado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/ncompareceu/{idAgenda}")
    public ResponseEntity<?> updateStatusNCompareceu(
            @RequestBody Agenda agenda,
            @RequestBody Secretaria secretaria,
            @RequestBody String observacao,
            @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.updateStatusNCompareceu(idAgenda,agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Status Da Agenda Alterado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/cancelado/{idAgenda}")
    public ResponseEntity<?> updateStatusCancelado(
            @RequestBody Agenda agenda,
            @RequestBody Secretaria secretaria,
            @RequestBody String observacao,
            @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.updateStatusCancelado(idAgenda,agenda,secretaria,observacao);
            return ResponseEntity.ok().body("Agenda Cancelada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idAgenda}")
    public ResponseEntity<?> disable(
            @RequestBody Agenda agenda,
            @PathVariable("idAgenda") Long idAgenda
    ){
        try {
            this.agendaService.disable(idAgenda, agenda);
            return ResponseEntity.ok().body("Agenda Desabilitada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}