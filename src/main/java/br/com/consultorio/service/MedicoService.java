package br.com.consultorio.service;

import br.com.consultorio.entity.Medico;
import br.com.consultorio.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Optional<Medico> findById(Long id){
        return this.medicoRepository.findById(id);
    }

    public Page<Medico> listAll(Pageable pageable){
        return this.medicoRepository.findAll(pageable);
    }

    public Page<Medico> findByName(String name, Pageable pageable) {
        return this.medicoRepository.findAllByNomeContaining(name, pageable);
    }

    @Transactional
    public void update(Long id, Medico medico) {
        if (id == medico.getId()) {
            this.medicoRepository.save(medico);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insert(Medico medico){
        this.medicoRepository.save(medico);
    }

    @Transactional
    public void disable(Long id, Medico medico) {
        if (id == medico.getId()) {
            this.medicoRepository.disable(medico.getId(), false);
        } else {
            throw new RuntimeException();
        }
    }
}
