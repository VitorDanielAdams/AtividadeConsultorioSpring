package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "pacientes", schema = "public")
public class Paciente extends Pessoa {

    @Getter @Setter
    @Column(name = "tipo_atendimento", nullable = false, length = 11)
    private TipoAtendimento tipoAtendimento;

    @Getter @Setter
    @JoinColumn(name = "id_convenio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Convenio convenio;

    @Getter @Setter
    @Column(name = "numero_cartao_convenio", nullable = false, length = 20)
    private String numeroCartaoConvenio;

    @Getter @Setter
    @Column(name = "data_vencimento", nullable = false)
    private LocalDateTime dataVencimento;

}
