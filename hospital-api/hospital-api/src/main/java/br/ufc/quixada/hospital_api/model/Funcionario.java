package br.ufc.quixada.hospital_api.model; // Verifique se seu package está correto

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"   //adiciona uma propriedade extra no JSON de "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MedicoEfetivo.class, name = "MedicoEfetivo"),
        @JsonSubTypes.Type(value = MedicoPlantonista.class, name = "MedicoPlantonista"),
        @JsonSubTypes.Type(value = Enfermeiro.class, name = "Enfermeiro")
})

public abstract class Funcionario {
    protected String matricula;
    protected String nome;

    // Construtor padrão da classe
    public Funcionario(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    // Construtor padrão vazio para o JSON
    public Funcionario() {}

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public abstract double calcularSalario();
    public abstract String obterDetalhes();
}