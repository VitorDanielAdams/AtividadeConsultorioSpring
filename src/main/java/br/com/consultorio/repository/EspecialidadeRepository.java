package br.com.consultorio.repository;

import br.com.consultorio.entity.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    @Modifying
    @Query("UPDATE Especialidade especialidade " +
            "SET especialidade.ativo = :excluido " +
            "WHERE especialidade.id = :especialidade")
    public void disable(
            @Param("especialidade") Long idEspecialidade,
            @Param("excluido") Boolean ativo);

    public Page<Especialidade> findAllByNomeContaining(String name, Pageable pageable);
}
