package br.com.consultorio.service;

import br.com.consultorio.entity.*;
import br.com.consultorio.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HistoricoService {

    @Autowired
    public HistoricoRepository historicoRepository;

    public void createHistorico(Agenda agenda, StatusAgendamento statusAgendamento,
                                     LocalDateTime data, Paciente paciente, Secretaria secretaria, String obs) {
        Historico historico = new Historico(agenda,statusAgendamento,paciente,secretaria,data,obs);
        this.insert(historico);
    }

    public void insert(Historico historico) {
        this.validarInsert(historico);
        this.saveTransaction(historico);
    }

    public void validarInsert(Historico historico) {

    }

    @Transactional
    public void saveTransaction(Historico historico) {
        this.historicoRepository.save(historico);
    }

    public Optional<Historico> findById(Long id) {
        return this.historicoRepository.findById(id);
    }

    public Page<Historico> listAll(Pageable pageable) {
        return this.historicoRepository.findAll(pageable);
    }

}
