package br.com.consultorio.service;

import br.com.consultorio.entity.Convenio;
import br.com.consultorio.repository.ConvenioRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    public Optional<Convenio> findById(Long id){
        return this.convenioRepository.findById(id);
    }

    public Page<Convenio> listAll(Pageable pageable){
        return this.convenioRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, Convenio convenio) {
        if (id == convenio.getId()) {
            this.convenioRepository.save(convenio);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void insert(Convenio convenio){
        this.convenioRepository.save(convenio);
    }

    @Transactional
    public void disable(Long id, Convenio convenio) {
        if (id == convenio.getId()) {
            this.convenioRepository.disable(convenio.getId(), LocalDateTime.now());
        } else {
            throw new RuntimeException();
        }
    }
}
