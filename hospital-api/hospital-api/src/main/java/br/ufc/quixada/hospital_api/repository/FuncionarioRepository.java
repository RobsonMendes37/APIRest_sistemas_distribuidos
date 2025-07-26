package br.ufc.quixada.hospital_api.repository;

import br.ufc.quixada.hospital_api.model.Enfermeiro;
import br.ufc.quixada.hospital_api.model.Funcionario;
import br.ufc.quixada.hospital_api.model.MedicoEfetivo;
import br.ufc.quixada.hospital_api.model.MedicoPlantonista;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class FuncionarioRepository {

    private final Map<String, Funcionario> funcionariosDB = new ConcurrentHashMap<>();

    //FUNCIONARIOS
     //Salva ou atualiza qualquer tipo de funcionário
     //todos os outros métodos de salvar usarão.
    public Funcionario save(Funcionario funcionario) {
        funcionariosDB.put(funcionario.getMatricula(), funcionario);
        return funcionario;
    }

    //Encontra qualquer funcionário pela matrícula.
    public Optional<Funcionario> findByMatricula(String matricula) {
        return Optional.ofNullable(funcionariosDB.get(matricula));
    }

    //Retorna uma lista com todos os funcionários, independente do tipo
    public List<Funcionario> findAll() {
        return new ArrayList<>(funcionariosDB.values());
    }

    //Deleta qualquer funcionário do banco de dados pela matrícula.
    public boolean deleteByMatricula(String matricula) {
        return funcionariosDB.remove(matricula) != null;
    }

    //Verifica se uma matrícula já existe no banco.
    public boolean existsByMatricula(String matricula) {
        return funcionariosDB.containsKey(matricula);
    }




    //ENFERMEIROS

    //CREATE / UPDATE: Salva ou atualiza um enfermeiro específico.
    public Enfermeiro saveEnfermeiro(Enfermeiro enfermeiro) {
        return (Enfermeiro) save(enfermeiro);
    }

    //Encontra um enfermeiro pela matrícula
    public Optional<Enfermeiro> findEnfermeiroByMatricula(String matricula) {
        return findByMatricula(matricula)
                .filter(func -> func instanceof Enfermeiro)
                .map(func -> (Enfermeiro) func);
    }

    //Lista todos os enfermeiros
    public List<Enfermeiro> findAllEnfermeiros() {
        return funcionariosDB.values().stream()
                .filter(f -> f instanceof Enfermeiro)
                .map(f -> (Enfermeiro) f)
                .collect(Collectors.toList());
    }


    //MEDICOS_EFETIVOS

    //Salva ou atualiza um médico efetivo específico.
    public MedicoEfetivo saveMedicoEfetivo(MedicoEfetivo medico) {
        return (MedicoEfetivo) save(medico);
    }

    //Encontra um médico efetivo pela matrícula.
    public Optional<MedicoEfetivo> findMedicoEfetivoByMatricula(String matricula) {
        return findByMatricula(matricula)
                .filter(func -> func instanceof MedicoEfetivo)
                .map(func -> (MedicoEfetivo) func);
    }

    //Lista todos os médicos efetivos.
    public List<MedicoEfetivo> findAllMedicosEfetivos() {
        return funcionariosDB.values().stream()
                .filter(f -> f instanceof MedicoEfetivo)
                .map(f -> (MedicoEfetivo) f)
                .collect(Collectors.toList());
    }




    // MEDICO_PLANTONISTAS
    //CREATE / UPDATE: Salva ou atualiza um médico plantonista específico.
    public MedicoPlantonista saveMedicoPlantonista(MedicoPlantonista medico) {
        return (MedicoPlantonista) save(medico);
    }

    //READ: Encontra um médico plantonista pela matrícula.
    public Optional<MedicoPlantonista> findMedicoPlantonistaByMatricula(String matricula) {
        return findByMatricula(matricula)
                .filter(func -> func instanceof MedicoPlantonista)
                .map(func -> (MedicoPlantonista) func);
    }

    //READ: Lista todos os médicos plantonistas.
    public List<MedicoPlantonista> findAllMedicosPlantonistas() {
        return funcionariosDB.values().stream()
                .filter(f -> f instanceof MedicoPlantonista)
                .map(f -> (MedicoPlantonista) f)
                .collect(Collectors.toList());
    }
}