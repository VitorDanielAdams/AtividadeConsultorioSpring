package br.com.consultorio.service;

import br.com.consultorio.entity.Paciente;
import br.com.consultorio.entity.TipoAtendimento;
import br.com.consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    public PacienteRepository pacienteRepository;

    public void insert(Paciente paciente){
        this.validarFormulario(paciente);
        this.saveTransaction(paciente);
    }

    @Transactional
    public void saveTransaction (Paciente paciente) {
        this.pacienteRepository.save(paciente);
    }

    public void validarFormulario (Paciente paciente) {

        if (paciente.getTipoAtendimento().equals(TipoAtendimento.convenio)) {
            if (paciente.getConvenio() == null || paciente.getConvenio().getId() == null) {
                throw new RuntimeException("Warning:Convênio não informado!");
            }
            if (paciente.getNumeroCartaoConvenio() == null) {
                throw new RuntimeException("Warning:Cartão do Convênio não informado!");
            }
            if (paciente.getDataVencimento() == null) {
                throw new RuntimeException("Warning:Data de vencimento do cartão não informada!");
            }
            if (paciente.getDataVencimento().compareTo(LocalDateTime.now()) > 0) {
                throw new RuntimeException("Warning:Data de vencimento do cartão está expirada!");
            }
        }

        if (paciente.getTipoAtendimento().equals(TipoAtendimento.particular)){
            paciente.setConvenio(null);
            paciente.setNumeroCartaoConvenio(null);
            paciente.setDataVencimento(null);
        }

    }

    public Optional<Paciente> findById(Long id) {
        return this.pacienteRepository.findById(id);
    }

    public Page<Paciente> listAll(Pageable pageable) {
        return this.pacienteRepository.findAll(pageable);
    }

    public Page<Paciente> findByName(String name, Pageable pageable) {
        return this.pacienteRepository.findAllByNomeContaining(name, pageable);
    }

    @Transactional
    public void update(Long id, Paciente paciente) {
        if (id == paciente.getId()){
            this.validarFormulario(paciente);
            this.saveTransaction(paciente);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void disable(Long id, Paciente paciente) {
        if (id == paciente.getId()) {
            this.pacienteRepository.disable(paciente.getId(), false);
        } else {
            throw new RuntimeException();
        }
    }
}
