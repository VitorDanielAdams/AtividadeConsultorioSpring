package br.com.consultorio.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@MappedSuperclass
public abstract class Pessoa extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Getter @Setter
    @Column(name = "rg", nullable = false, length = 14)
    private String rg;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 18)
    private String telefone;

    @Getter @Setter
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Getter @Setter
    @Column(name = "login", nullable = false, length = 30)
    private String login;

    @Getter @Setter
    @Column(name = "senha", nullable = false, length = 32)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Getter @Setter
    @Column(name = "nacionalidade", nullable = false, length = 50)
    private String nacionalidade;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false, length = 10)
    private Sexo sexo;
}
