package br.ufc.quixada.hospital_api.model;

public class Enfermeiro extends Funcionario {
    private String coren;
    private double salarioBase;

    public Enfermeiro(String matricula, String nome, String coren, double salarioBase) {
        super(matricula, nome);
        this.coren = coren;
        this.salarioBase = salarioBase;
    }

    public Enfermeiro() {
        super();
    }

    @Override
    public double calcularSalario() {
        return salarioBase;
    }

    @Override
    public String obterDetalhes() {
        return String.format("Enfermeiro | COREN: %s | Salario: R$ %.2f", coren, calcularSalario());
    }

    /// gets e sets
    public String getCoren() { return coren; }
    public void setCoren(String coren) { this.coren = coren; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
}
