package br.com.consultorio.repository;

import br.com.consultorio.entity.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Modifying
    @Query("UPDATE Medico medico " +
            "SET medico.ativo = :excluido " +
            "WHERE medico.id = :medico")
    public void disable(
            @Param("medico") Long idMedico,
            @Param("excluido") Boolean ativo);

    public Page<Medico> findAllByNomeContaining(String name, Pageable pageable);
}
