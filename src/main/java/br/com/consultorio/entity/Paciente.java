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
@Table(name = "pacientes", schema = "public")
public class Paciente extends Pessoa {

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atendimento", nullable = false, length = 11)
    private TipoAtendimento tipoAtendimento;

    @Getter @Setter
    @JoinColumn(name = "id_convenio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Convenio convenio;

    @Getter @Setter
    @Column(name = "numero_cartao_convenio", length = 20)
    private String numeroCartaoConvenio;

    @Getter @Setter
    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

}
