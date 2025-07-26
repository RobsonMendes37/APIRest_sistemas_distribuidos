package br.ufc.quixada.hospital_api.model;

public class MedicoPlantonista extends Medico {
    private int horasTrabalhadas;


    private double valorHoraPlatao;

    public MedicoPlantonista(String matricula, String nome, String crm, int horasTrabalhadas, double valorHoraPlatao) {
        super(matricula, nome, crm);
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHoraPlatao = valorHoraPlatao;
    }

    @Override
    public double calcularSalario() {
        return this.horasTrabalhadas * this.valorHoraPlatao;
    }

    @Override
    public String obterDetalhes() {
        return String.format("Medico Plantonista | CRM: %s | Horas: %d | Salario: R$ %.2f", crm, horasTrabalhadas, calcularSalario());
    }


    public void setHorasTrabalhadas(Integer horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }
    public void setValorHoraPlatao(Double valorHoraPlatao) {
        this.valorHoraPlatao = valorHoraPlatao;
    }
    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }
    public double getValorHoraPlatao() {
        return valorHoraPlatao;
    }
}