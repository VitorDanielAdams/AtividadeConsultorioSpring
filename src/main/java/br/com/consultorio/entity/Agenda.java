package br.com.consultorio.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "agendas", schema = "public")
public class Agenda extends AbstractEntity {

    @Getter @Setter
    @ManyToOne
    private Paciente paciente;

    @Getter @Setter
    @ManyToOne
    private Medico medico;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status_agendamento", nullable = false, length = 15)
    private StatusAgendamento statusAgendamento;

    @Getter @Setter
    @Column(name = "data_de",nullable = false)
    private LocalDateTime dataDe;

    @Getter @Setter
    @Column(name = "data_ate",nullable = false)
    private LocalDateTime dataAte;

    @Getter @Setter
    @Column(name = "encaixe", columnDefinition = "boolean default false", nullable = false)
    private Boolean encaixe;

}
