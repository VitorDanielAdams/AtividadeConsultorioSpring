package br.com.consultorio.service;

import br.com.consultorio.entity.Paciente;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.repository.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecretariaService {

    @Autowired
    private SecretariaRepository secretariaRepository;

    public Optional<Secretaria> findById(Long id){
        return this.secretariaRepository.findById(id);
    }

    public Page<Secretaria> listAll(Pageable pageable){
        return this.secretariaRepository.findAll(pageable);
    }

    public Page<Secretaria> findByName(String name, Pageable pageable) {
        return this.secretariaRepository.findAllByNomeContaining(name, pageable);
    }

    @Transactional
    public void update(Long id, Secretaria secretaria) {
        if (id == secretaria.getId()) {
            this.secretariaRepository.save(secretaria);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insert(Secretaria secretaria){
        this.secretariaRepository.save(secretaria);
    }

    @Transactional
    public void disable(Long id, Secretaria secretaria) {
        if (id == secretaria.getId()) {
            this.secretariaRepository.disable(secretaria.getId(), false);
        } else {
            throw new RuntimeException();
        }
    }

}
