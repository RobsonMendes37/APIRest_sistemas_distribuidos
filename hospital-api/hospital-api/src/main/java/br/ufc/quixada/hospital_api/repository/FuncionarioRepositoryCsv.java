package br.ufc.quixada.hospital_api.repository;

import br.ufc.quixada.hospital_api.model.*; // Importa todos os modelos
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class FuncionarioRepositoryCsv{

    // Define um nome e um cabeçalho padrão para nosso arquivo de "banco de dados"
    private static final String CSV_FILE_PATH = "hospital_data.csv";
    private static final String CSV_HEADER = "tipo,matricula,nome,crm,coren,salarioBase,horasTrabalhadas,valorHoraPlatao";


    // O Map ainda existe mas agora ele funciona como um  em memória auxiliar
    private final Map<String, Funcionario> funcionariosDB = new ConcurrentHashMap<>();


    //A anotação @PostConstruct diz ao Spring para executar este método automaticamente
    //logo após a inicialização do servidor. Ele carrega os dados do arquivo para a memória.
    @PostConstruct
    private void initCsvDatabase() {
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            System.out.println(">>> Arquivo CSV não encontrado. Criando um novo...");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
                writer.write(CSV_HEADER);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("!!! Erro Crítico: Não foi possível criar o arquivo CSV: " + e.getMessage());
            }
        } else {
            System.out.println(">>> Carregando dados do arquivo CSV existente...");
            try {
                readFromFile();
            } catch (IOException e) {
                System.err.println("!!! Erro Crítico: Não foi possível ler o arquivo CSV: " + e.getMessage());
            }
        }
    }

    //Lê o arquivo CSV linha por linha e popula o Map em memória./
    private void readFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            reader.readLine(); // Pula a linha do cabeçalho

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Ignora linhas em branco
                String[] values = line.split(",", -1); // -1 para incluir campos vazios no final
                Funcionario f = createFuncionarioFromCsv(values);
                if (f != null) {
                    funcionariosDB.put(f.getMatricula(), f);
                }
            }
        }
    }

    //Reescreve o arquivo CSV inteiro com os dados atuais do Map.
     //O 'synchronized' garante que o arquivo não seja escrito por duas operações ao mesmo tempo.
    private synchronized void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            writer.write(CSV_HEADER);
            writer.newLine();
            for (Funcionario f : funcionariosDB.values()) {
                writer.write(createCsvFromFuncionario(f));
                writer.newLine();
            }
        } catch (IOException e) {
            // Em uma aplicação real, aqui teríamos um sistema de log mais robusto
            System.err.println("!!! ERRO DE PERSISTÊNCIA: Não foi possível escrever no arquivo CSV.");
            e.printStackTrace();
        }
    }


    // ===========Metodos das entidades========


    public Funcionario save(Funcionario funcionario) {
        funcionariosDB.put(funcionario.getMatricula(), funcionario);
        writeToFile(); // Salva as mudanças no arquivo a cada alteração
        return funcionario;
    }

    public boolean deleteByMatricula(String matricula) {
        if (funcionariosDB.remove(matricula) != null) {
            writeToFile(); // Salva as mudanças no arquivo
            return true;
        }
        return false;
    }

    // Métodos de leitura não precisam mudar, pois leem do "cache" em memória
    public Optional<Funcionario> findByMatricula(String matricula) { return Optional.ofNullable(funcionariosDB.get(matricula)); }
    public List<Funcionario> findAll() { return new ArrayList<>(funcionariosDB.values()); }
    public boolean existsByMatricula(String matricula) { return funcionariosDB.containsKey(matricula); }

    // Métodos de salvar específicos
    public Enfermeiro saveEnfermeiro(Enfermeiro e) { return (Enfermeiro) save(e); }
    public MedicoEfetivo saveMedicoEfetivo(MedicoEfetivo me) { return (MedicoEfetivo) save(me); }
    public MedicoPlantonista saveMedicoPlantonista(MedicoPlantonista mp) { return (MedicoPlantonista) save(mp); }

    // Métodos de consulta específicos
    public List<Enfermeiro> findAllEnfermeiros() { return funcionariosDB.values().stream().filter(Enfermeiro.class::isInstance).map(Enfermeiro.class::cast).collect(Collectors.toList()); }
    public Optional<Enfermeiro> findEnfermeiroByMatricula(String matricula) { return findByMatricula(matricula).filter(Enfermeiro.class::isInstance).map(Enfermeiro.class::cast); }
    public List<MedicoEfetivo> findAllMedicosEfetivos() { return funcionariosDB.values().stream().filter(MedicoEfetivo.class::isInstance).map(MedicoEfetivo.class::cast).collect(Collectors.toList()); }
    public Optional<MedicoEfetivo> findMedicoEfetivoByMatricula(String matricula) { return findByMatricula(matricula).filter(MedicoEfetivo.class::isInstance).map(MedicoEfetivo.class::cast); }
    public List<MedicoPlantonista> findAllMedicosPlantonistas() { return funcionariosDB.values().stream().filter(MedicoPlantonista.class::isInstance).map(MedicoPlantonista.class::cast).collect(Collectors.toList()); }
    public Optional<MedicoPlantonista> findMedicoPlantonistaByMatricula(String matricula) { return findByMatricula(matricula).filter(MedicoPlantonista.class::isInstance).map(MedicoPlantonista.class::cast); }



    // =========== MUDA CSV <-> OBJETO ==========================
    private String createCsvFromFuncionario(Funcionario f) {
        String tipo = f.getClass().getSimpleName();
        String matricula = f.getMatricula();
        String nome = f.getNome();
        String crm = (f instanceof Medico) ? ((Medico) f).getCrm() : "";
        String coren = (f instanceof Enfermeiro) ? ((Enfermeiro) f).getCoren() : "";

        double salarioBase = 0.0;
        if (f instanceof Enfermeiro) salarioBase = ((Enfermeiro) f).getSalarioBase();
        if (f instanceof MedicoEfetivo) salarioBase = ((MedicoEfetivo) f).getSalarioBase();

        int horas = (f instanceof MedicoPlantonista) ? ((MedicoPlantonista) f).getHorasTrabalhadas() : 0;
        double valorHora = (f instanceof MedicoPlantonista) ? ((MedicoPlantonista) f).getValorHoraPlatao() : 0.0;

        return String.join(",", tipo, matricula, nome, crm, coren, String.valueOf(salarioBase), String.valueOf(horas), String.valueOf(valorHora));
    }

    private Funcionario createFuncionarioFromCsv(String[] values) {
        String tipo = values[0];
        String matricula = values[1];
        String nome = values[2];
        String crm = values[3];
        String coren = values[4];
        double salarioBase = Double.parseDouble(values[5]);
        int horas = Integer.parseInt(values[6]);
        double valorHora = Double.parseDouble(values[7]);

        return switch (tipo) {
            case "Enfermeiro" -> new Enfermeiro(matricula, nome, coren, salarioBase);
            case "MedicoEfetivo" -> new MedicoEfetivo(matricula, nome, crm, salarioBase);
            case "MedicoPlantonista" -> new MedicoPlantonista(matricula, nome, crm, horas, valorHora);
            default -> null;
        };
    }
}