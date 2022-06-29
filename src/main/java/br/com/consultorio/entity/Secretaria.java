package br.com.consultorio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NoArgsConstructor
@Table(name = "secretarias", schema = "public")
public class Secretaria extends Pessoa {

    @Getter @Setter
    @Digits(integer = 5, fraction = 3)
    @Column(name = "salario", nullable = false)
    private BigDecimal salario;

    @Getter @Setter
    @Column(name = "data_contratacao", nullable = false)
    private LocalDateTime dataContratacao;

    @Getter @Setter
    @Column(name = "pis", nullable = false, length = 15)
    private String pis;

}
