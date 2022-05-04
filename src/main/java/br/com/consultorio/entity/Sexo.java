package br.com.consultorio.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Sexo {
    masculino("Maculino"),
    feminino("Feminino"),
    outro("Outros");

    public final String valor;

    private Sexo(String valor){
        this.valor = valor;
    }
}
