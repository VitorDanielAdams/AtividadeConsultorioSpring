package br.com.consultorio.repository;

import br.com.consultorio.entity.Agenda;
import br.com.consultorio.entity.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Modifying
    @Query("UPDATE Agenda agenda " +
            "SET agenda.excluido = :excluido " +
            "WHERE agenda.id = :agenda")
    public void disable(
            @Param("agenda") Long idAgenda,
            @Param("excluido") LocalDateTime dataExcluido);

    @Query("SELECT Agenda FROM Agenda " +
            "WHERE :datade BETWEEN Agenda.dataDe AND Agenda.dataAte " +
            "AND :dataAte BETWEEN Agenda.dataDe AND Agenda.dataAte " +
            "AND Agenda.medico = :medico " +
            "AND Agenda.paciente = :paciente")
    public List<Agenda> conflitoMedicoPaciente(
            @Param("datade") LocalDateTime dataDe,
            @Param("dataate") LocalDateTime dataAte,
            @Param("medico") Long idMedico,
            @Param("paciente") Long idPaciente
    );
}
