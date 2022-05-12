package br.com.consultorio.repository;

import br.com.consultorio.entity.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {

    @Modifying
    @Query("UPDATE Historico historico " +
            "SET historico.excluido = :excluido " +
            "WHERE historico.id = :historico")
    public void disable(
            @Param("historico") Long idHistorico,
            @Param("excluido")LocalDateTime dataExcluido);
}
