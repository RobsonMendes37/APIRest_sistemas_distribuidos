package br.ufc.quixada.hospital_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MedicoPlantonistaDTO {

    @NotBlank(message = "A matrícula não pode ser vazia.")
    private String matricula;

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O CRM não pode ser vazio.")
    private String crm;

    @NotNull(message = "A quantidade de horas trabalhadas é obrigatória.")
    @Positive(message = "As horas devem ser um valor positivo.")
    private Integer horasTrabalhadas;

    @NotNull(message = "O valor por hora de plantão é obrigatório.")
    @Positive(message = "O valor da hora deve ser positivo.")
    private Double valorHoraPlatao;


    // Getters e Setters
    public String getMatricula() { return matricula;    }
    public void setMatricula(String matricula) {this.matricula = matricula;    }
    public String getNome() {  return nome;    }
    public void setNome(String nome) {  this.nome = nome;    }
    public String getCrm() {   return crm;    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
    public Integer getHorasTrabalhadas() {return horasTrabalhadas;    }
    public void setHorasTrabalhadas(Integer horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }
    public Double getValorHoraPlatao() { return valorHoraPlatao;    }
    public void setValorHoraPlatao(Double valorHoraPlatao) {
        this.valorHoraPlatao = valorHoraPlatao;
    }
}