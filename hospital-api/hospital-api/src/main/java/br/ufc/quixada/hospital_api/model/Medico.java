package br.ufc.quixada.hospital_api.model; // Verifique seu package

public abstract class Medico extends Funcionario {
    protected String crm;

    // Construtor corrigido
    public Medico(String matricula, String nome, String crm) {
        super(matricula, nome); // Chama o construtor da classe pai (Funcionario)
        this.crm = crm;
    }

    public Medico() {
        super(); // Chama o construtor padrão do pai
    }

    // Getters e Setters
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
}