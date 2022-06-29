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
            "SET agenda.ativo = :excluido " +
            "WHERE agenda.id = :agenda")
    public void disable(
            @Param("agenda") Long idAgenda,
            @Param("excluido") Boolean ativo);

    @Query("FROM Agenda agenda " +
            "WHERE (:datade BETWEEN agenda.dataDe AND agenda.dataAte " +
            "OR :dataAte BETWEEN agenda.dataDe AND agenda.dataAte) " +
            "AND (agenda.medico.id = :medico OR agenda.paciente.id = :paciente) " +
            "AND agenda.id <> :agenda")
    public List<Agenda> conflitoMedicoPaciente(
            @Param("agenda") Long idAgenda,
            @Param("datade") LocalDateTime dataDe,
            @Param("dataAte") LocalDateTime dataAte,
            @Param("medico") Long idMedico,
            @Param("paciente") Long idPaciente
    );
}
