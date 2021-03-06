package br.com.consultorio.repository;

import br.com.consultorio.entity.Secretaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {

    @Modifying
    @Query("UPDATE Secretaria secretaria " +
            "SET secretaria.ativo = :excluido " +
            "WHERE secretaria.id = :secretaria")
    public void disable(
            @Param("secretaria") Long idSecretaria,
            @Param("excluido") Boolean ativo);

    public Page<Secretaria> findAllByNomeContaining(String name, Pageable pageable);
}
