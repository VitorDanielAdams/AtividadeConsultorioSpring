package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Table(name = "medicos", schema = "public")
public class Medico extends Pessoa {

    @Getter @Setter
    @JoinColumn(name = "id_especialidade")
    @ManyToOne(fetch = FetchType.LAZY)
    private Especialidade especialidade;

    @Getter @Setter
    @Column(name = "crm", nullable = false, length = 20, unique = true)
    private String CRM;

    @Getter @Setter
    @Digits(integer = 3, fraction = 3)
    @Column(name = "porcen_participacao", nullable = false)
    private BigDecimal porcenParticipacao;

    @Getter @Setter
    @Column(name = "consultorio", nullable = false, length = 20)
    private String consultorio;

}
