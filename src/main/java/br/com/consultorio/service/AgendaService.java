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
import org.springframework.util.Assert;

import java.time.DayOfWeek;
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

    public boolean dataMaiorAtual(LocalDateTime data){
        return data.compareTo(LocalDateTime.now()) >= 0 ? true : false;
    }

    public boolean dataMenorIgualAtual(LocalDateTime data){
        return data.compareTo(LocalDateTime.now()) <= 0 ? true : false;
    }

    public boolean dataDeMaiorDataAte(LocalDateTime dataDe, LocalDateTime dataAte){
        return dataAte.compareTo(dataDe) >= 0 ? true : false;
    }

    public boolean horarioComercial(LocalDateTime data){
        if ((data.getHour() >= 8 && data.getHour() < 12)
            || (data.getHour() >= 14 && data.getHour() < 18)
        ){
            return true;
        } else {
            return false;
        }
    }

    public boolean diaSemana(LocalDateTime data){
       return data.getDayOfWeek().equals(DayOfWeek.SATURDAY)
               || data.getDayOfWeek().equals(DayOfWeek.SUNDAY) ? false : true;
    }

    public boolean horarioMedico(Agenda agenda){
        return this.agendaRepository.conflitoMedicoPaciente(
                agenda.getId(),
                agenda.getDataDe(),
                agenda.getDataAte(),
                agenda.getMedico().getId(),
                agenda.getPaciente().getId()).size() > 0 ? false : true;
    }

    public void validarEncaixeTrue(Agenda agenda) {
        Assert.isTrue(this.dataMaiorAtual(agenda.getDataAte()),
                "Warning: Não pode ser agendado uma consulta no passado");
        Assert.isTrue(this.dataMaiorAtual(agenda.getDataDe()),
                "Warning: Não pode ser agendado uma consulta no passado");
        Assert.isTrue(this.dataDeMaiorDataAte(agenda.getDataDe(),agenda.getDataAte()),
                "Warning: Data inválida");
        Assert.isTrue(this.horarioComercial(agenda.getDataDe()),
                "Warning: Não pode ser agendado uma consulta fora do horário comercial");
        Assert.isTrue(this.horarioComercial(agenda.getDataAte()),
                "Warning: Não pode ser agendado uma consulta fora do horário comercial");
        Assert.isTrue(this.diaSemana(agenda.getDataDe()),
                "Warning: Não pode ser agendado uma consulta no final de semana");
        Assert.isTrue(this.diaSemana(agenda.getDataAte()),
                "Warning: Não pode ser agendado uma consulta no final de semana");
        Assert.isTrue(this.horarioMedico(agenda),
                "Warning: O horário do agendamento está ocupado");
    }

    public void validarEncaixeFalse(Agenda agenda) {
        Assert.isTrue(this.horarioMedico(agenda),
                "Warning: O horário do agendamento está ocupado");
    }

    public void validarInsert(Agenda agenda, Secretaria secretaria) {

        if (secretaria == null) {
            if(agenda.getEncaixe()){
                this.validarEncaixeTrue(agenda);
                agenda.setStatusAgendamento(StatusAgendamento.pendente);
            } else {
                this.validarEncaixeFalse(agenda);
                agenda.setStatusAgendamento(StatusAgendamento.pendente);
            }
        } else {
            if(agenda.getEncaixe()){
                this.validarEncaixeTrue(agenda);
                agenda.setStatusAgendamento(StatusAgendamento.aprovado);
            } else {
                this.validarEncaixeFalse(agenda);
                agenda.setStatusAgendamento(StatusAgendamento.aprovado);
            }
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
        if(agenda.getEncaixe()){
            this.validarEncaixeTrue(agenda);
        } else {
            this.validarEncaixeFalse(agenda);
        }
    }

    public void updateStatusAprovado(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateAprovado(agenda, secretaria);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateAprovado(Agenda agenda, Secretaria secretaria) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            if(secretaria != null){
                agenda.setStatusAgendamento(StatusAgendamento.aprovado);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Aprovado");
        }
    }

    public void updateStatusRejeitado(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateRejeitado(agenda, secretaria);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateRejeitado(Agenda agenda, Secretaria secretaria) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            if(secretaria != null){
                agenda.setStatusAgendamento(StatusAgendamento.rejeitado);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Rejeitado");
        }
    }

    public void updateStatusCancelado(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateCancelado(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCancelado(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)
            || agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            agenda.setStatusAgendamento(StatusAgendamento.rejeitado);
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Cancelado");
        }
    }

    public void updateStatusCompareceu(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateCompareceu(agenda, secretaria);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCompareceu(Agenda agenda, Secretaria secretaria) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            if(secretaria != null) {
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataAte()),
                        "Warning: Data inválida");
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataDe()),
                        "Warning: Data inválida");
                agenda.setStatusAgendamento(StatusAgendamento.compareceu);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Compareceu");
        }
    }

    public void updateStatusNCompareceu(Long id,Agenda agenda,Secretaria secretaria,String observacao) {
        if (id == agenda.getId()) {
            this.validarUpdateNCompareceu(agenda, secretaria);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), secretaria,observacao);
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateNCompareceu(Agenda agenda, Secretaria secretaria) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            if(secretaria != null) {
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataAte()),
                        "Warning: Data inválida");
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataDe()),
                        "Warning: Data inválida");
                agenda.setStatusAgendamento(StatusAgendamento.ncompareceu);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
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
