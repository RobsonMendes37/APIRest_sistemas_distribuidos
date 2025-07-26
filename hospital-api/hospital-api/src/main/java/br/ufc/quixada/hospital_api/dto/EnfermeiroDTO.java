package br.ufc.quixada.hospital_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// DTO: Objeto de Transferência de Dados para criar um Enfermeiro.
public class EnfermeiroDTO {

    @NotBlank(message = "A matrícula não pode ser vazia.")
    private String matricula;

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O COREN não pode ser vazio.")
    private String coren;

    @NotNull(message = "O salário base é obrigatório.")
    @Positive(message = "O salário deve ser um valor positivo.")
    private Double salarioBase;
    
    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCoren() { return coren; }
    public void setCoren(String coren) { this.coren = coren; }
    public Double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(Double salarioBase) { this.salarioBase = salarioBase; }
}