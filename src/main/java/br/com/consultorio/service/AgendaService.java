package br.com.consultorio.service;

import br.com.consultorio.entity.Agenda;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.entity.StatusAgendamento;
import br.com.consultorio.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    public AgendaRepository agendaRepository;

    @Autowired
    public HistoricoService historicoService;

    public void insert(Agenda agenda, Secretaria secretaria, String observacao) {
        this.validarInsert(agenda, secretaria);
        this.saveTransaction(agenda);
        this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                agenda.getPaciente(), secretaria,observacao);
    }

    public void validarInsert(Agenda agenda, Secretaria secretaria) {

        if (agenda.getDataDe().compareTo(agenda.getDataAte()) >= 0) {
            throw new RuntimeException("Warning: As datas são inválidas");
        }
        if(agenda.getStatusAgendamento() == null){
            agenda.setStatusAgendamento(StatusAgendamento.pendente);
        }
        if (secretaria == null) {
            agenda.setStatusAgendamento(StatusAgendamento.pendente);
        }

    }

    @Transactional
    public void saveTransaction(Agenda agenda) {
        this.agendaRepository.save(agenda);
    }

    public Optional<Agenda> findById(Long id) {
        return this.agendaRepository.findById(id);
    }

    public Page<Agenda> listAll(Pageable pageable) {
        return this.agendaRepository.findAll(pageable);
    }

    public void update(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdate(agenda,secretaria);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdate(Agenda agenda, Secretaria secretaria) {
        if  (secretaria == null) {
            throw new RuntimeException("Warning: Operação invalida!");
        }
        if (agenda.getDataDe().compareTo(agenda.getDataAte()) >= 0) {
            throw new RuntimeException("Warning: As datas são inválidas");
        }
    }

    public void updateStatusAprovado(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateAprovado(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateAprovado(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            agenda.setStatusAgendamento(StatusAgendamento.aprovado);
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Aprovado");
        }
    }

    public void updateStatusRejeitado(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateRejeitado(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateRejeitado(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            agenda.setStatusAgendamento(StatusAgendamento.rejeitado);
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Rejeitado");
        }
    }

    public void updateStatusCompareceu(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateCompareceu(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCompareceu(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            agenda.setStatusAgendamento(StatusAgendamento.compareceu);
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Compareceu");
        }
    }

    public void updateStatusNCompareceu(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateNCompareceu(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateNCompareceu(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            agenda.setStatusAgendamento(StatusAgendamento.ncompareceu);
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Não Compareceu");
        }
    }

    @Transactional
    public void disable(Long id, Agenda agenda) {
        if (id == agenda.getId()){
            this.agendaRepository.disable(agenda.getId(), LocalDateTime.now());
        } else {
            throw new RuntimeException();
        }
    }

}
