package br.com.consultorio.repository;

import br.com.consultorio.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Modifying
    @Query("UPDATE Paciente paciente " +
            "SET paciente.ativo = :excluido " +
            "WHERE paciente.id = :paciente")
    public void disable(
            @Param("paciente") Long idPaciente,
            @Param("excluido") Boolean ativo);

}
