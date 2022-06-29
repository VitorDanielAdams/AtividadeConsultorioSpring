package br.com.consultorio.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NoArgsConstructor
@Table(name = "agendas", schema = "public")
public class Agenda extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name = "id_paciente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;

    @Getter @Setter
    @JoinColumn(name = "id_secretaria")
    @ManyToOne(fetch = FetchType.LAZY)
    private Secretaria secretaria;

    @Getter @Setter
    @JoinColumn(name = "id_medico")
    @ManyToOne(fetch = FetchType.LAZY)
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
