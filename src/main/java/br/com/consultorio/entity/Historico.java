package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "historicos", schema = "public")
public class Historico extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name = "id_agenda")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agenda agenda;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status_agendamento", nullable = false, length = 15)
    private StatusAgendamento statusAgendamento;

    @Getter @Setter
    @JoinColumn(name = "id_paciente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @Getter @Setter
    @JoinColumn(name = "id_secretaria")
    @ManyToOne(fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @Getter @Setter
    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Getter @Setter
    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

}
