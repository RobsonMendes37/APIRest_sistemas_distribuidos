package br.ufc.quixada.hospital_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MedicoEfetivoDTO {

    @NotBlank(message = "A matrícula não pode ser vazia.")
    private String matricula;

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O CRM não pode ser vazio.")
    private String crm;

    @NotNull(message = "O salário base é obrigatório.")
    @Positive(message = "O salário deve ser um valor positivo.")
    private Double salarioBase;


    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCrm() {
        return crm;
    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
    public Double getSalarioBase() {
        return salarioBase;
    }
    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }
}