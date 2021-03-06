package br.com.consultorio.service;

import br.com.consultorio.entity.Agenda;
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

    public void insert(Agenda agenda) {
        this.validarInsert(agenda);
        this.saveTransaction(agenda);
        this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                agenda.getPaciente(),agenda.getSecretaria());
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
                "Warning: N??o pode ser agendado uma consulta no passado");
        Assert.isTrue(this.dataMaiorAtual(agenda.getDataDe()),
                "Warning: N??o pode ser agendado uma consulta no passado");
        Assert.isTrue(this.dataDeMaiorDataAte(agenda.getDataDe(),agenda.getDataAte()),
                "Warning: Data inv??lida");
        Assert.isTrue(this.horarioComercial(agenda.getDataDe()),
                "Warning: N??o pode ser agendado uma consulta fora do hor??rio comercial");
        Assert.isTrue(this.horarioComercial(agenda.getDataAte()),
                "Warning: N??o pode ser agendado uma consulta fora do hor??rio comercial");
        Assert.isTrue(this.diaSemana(agenda.getDataDe()),
                "Warning: N??o pode ser agendado uma consulta no final de semana");
        Assert.isTrue(this.diaSemana(agenda.getDataAte()),
                "Warning: N??o pode ser agendado uma consulta no final de semana");
        Assert.isTrue(this.horarioMedico(agenda),
                "Warning: O hor??rio do agendamento est?? ocupado");
    }

    public void validarEncaixeFalse(Agenda agenda) {
        Assert.isTrue(this.horarioMedico(agenda),
                "Warning: O hor??rio do agendamento est?? ocupado");
    }

    public void validarInsert(Agenda agenda) {

        if (agenda.getSecretaria() == null) {
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

    public void update(Long id,Agenda agenda) {
        if (id == agenda.getId()) {
            this.validarUpdate(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), agenda.getSecretaria());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdate(Agenda agenda) {
        if(agenda.getEncaixe()){
            this.validarEncaixeTrue(agenda);
        } else {
            this.validarEncaixeFalse(agenda);
        }
    }

    public void updateStatusAprovado(Long id,Agenda agenda) {
        if (id == agenda.getId()) {
            this.validarUpdateAprovado(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), agenda.getSecretaria());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateAprovado(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            if(agenda.getSecretaria() != null){
                agenda.setStatusAgendamento(StatusAgendamento.aprovado);
            } else {
                throw new RuntimeException("Warning: Sem permiss??o para executar essa a????o");
            }
        } else {
            throw new RuntimeException("Warning: Status inv??lido para update de Aprovado");
        }
    }

    public void updateStatusRejeitado(Long id,Agenda agenda) {
        if (id == agenda.getId()) {
            this.validarUpdateRejeitado(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), agenda.getSecretaria());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateRejeitado(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            if(agenda.getSecretaria() != null){
                agenda.setStatusAgendamento(StatusAgendamento.rejeitado);
            } else {
                throw new RuntimeException("Warning: Sem permiss??o para executar essa a????o");
            }
        } else {
            throw new RuntimeException("Warning: Status inv??lido para update de Rejeitado");
        }
    }

    public void updateStatusCancelado(Long id,Agenda agenda) {
        if (id == agenda.getId()) {
            this.validarUpdateCancelado(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), agenda.getSecretaria());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCancelado(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.pendente)
            || agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            agenda.setStatusAgendamento(StatusAgendamento.rejeitado);
        } else {
            throw new RuntimeException("Warning: Status inv??lido para update de Cancelado");
        }
    }

    public void updateStatusCompareceu(Long id,Agenda agenda) {
        if (id == agenda.getId()) {
            this.validarUpdateCompareceu(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), agenda.getSecretaria());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCompareceu(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            if(agenda.getSecretaria() != null) {
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataAte()),
                        "Warning: Data inv??lida");
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataDe()),
                        "Warning: Data inv??lida");
                agenda.setStatusAgendamento(StatusAgendamento.compareceu);
            } else {
                throw new RuntimeException("Warning: Sem permiss??o para executar essa a????o");
            }
        } else {
            throw new RuntimeException("Warning: Status inv??lido para update de Compareceu");
        }
    }

    public void updateStatusNCompareceu(Long id,Agenda agenda) {
        if (id == agenda.getId()) {
            this.validarUpdateNCompareceu(agenda);
            this.saveTransaction(agenda);
            this.historicoService.createHistorico(agenda,agenda.getStatusAgendamento(),LocalDateTime.now(),
                    agenda.getPaciente(), agenda.getSecretaria());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateNCompareceu(Agenda agenda) {
        if (agenda.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            if(agenda.getSecretaria() != null) {
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataAte()),
                        "Warning: Data inv??lida");
                Assert.isTrue(this.dataMenorIgualAtual(agenda.getDataDe()),
                        "Warning: Data inv??lida");
                agenda.setStatusAgendamento(StatusAgendamento.nao_compareceu);
            } else {
                throw new RuntimeException("Warning: Sem permiss??o para executar essa a????o");
            }
        } else {
            throw new RuntimeException("Warning: Status inv??lido para update de N??o Compareceu");
        }
    }

    @Transactional
    public void disable(Long id, Agenda agenda) {
        if (id == agenda.getId()){
            this.agendaRepository.disable(agenda.getId(), false);
        } else {
            throw new RuntimeException();
        }
    }

}
