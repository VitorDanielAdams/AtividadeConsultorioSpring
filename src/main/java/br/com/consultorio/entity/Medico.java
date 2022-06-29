package br.com.consultorio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @Column(name = "porcentagem_participacao", nullable = false)
    private BigDecimal porcenParticipacao;

    @Getter @Setter
    @Digits(integer = 5, fraction = 3)
    @Column(name = "valor_consulta", nullable = false)
    private BigDecimal valorConsulta;

    @Getter @Setter
    @Column(name = "consultorio", nullable = false, length = 20)
    private String consultorio;

}
