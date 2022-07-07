package br.com.consultorio.service;

import br.com.consultorio.entity.Especialidade;
import br.com.consultorio.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public Optional<Especialidade> findById(Long id){
       return this.especialidadeRepository.findById(id);
    }

    public Page<Especialidade> listAll(Pageable pageable){
        return this.especialidadeRepository.findAll(pageable);
    }

    public Page<Especialidade> findByName(String name, Pageable pageable) {
        return this.especialidadeRepository.findAllByNomeContaining(name, pageable);
    }

    @Transactional
    public void update(Long id, Especialidade especialidade) {
        if (id == especialidade.getId()) {
            this.especialidadeRepository.save(especialidade);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insert(Especialidade especialidade){
        this.especialidadeRepository.save(especialidade);
    }

    @Transactional
    public void disable(Long id, Especialidade especialidade) {
        if (id == especialidade.getId()) {
            this.especialidadeRepository.disable(especialidade.getId(), false);
        } else {
            throw new RuntimeException();
        }
    }
}
