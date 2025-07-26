package br.ufc.quixada.hospital_api.service;

import br.ufc.quixada.hospital_api.dto.EnfermeiroDTO;
import br.ufc.quixada.hospital_api.dto.MedicoEfetivoDTO;
import br.ufc.quixada.hospital_api.dto.MedicoPlantonistaDTO;
import br.ufc.quixada.hospital_api.model.Enfermeiro;
import br.ufc.quixada.hospital_api.model.Funcionario;
import br.ufc.quixada.hospital_api.model.MedicoEfetivo;
import br.ufc.quixada.hospital_api.model.MedicoPlantonista;
import br.ufc.quixada.hospital_api.repository.FuncionarioRepository;
//import br.ufc.quixada.hospital_api.repository.FuncionarioRepositoryCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RHService {

    private final FuncionarioRepository repository;

    //para usar em no csv
    //private final FuncionarioRepository repositoryCsv;

    private final String nomeHospital = "Hospital Central de Capistrano";

    @Autowired
    public RHService(FuncionarioRepository repository) {
        this.repository = repository;
    }


    // Crud de Funcionarios
    public String getNomeHospital() {
        return nomeHospital;
    }

    public List<Funcionario> listarTodosFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> consultarFuncionario(String matricula) {
        return repository.findByMatricula(matricula);
    }

    public boolean demitirFuncionario(String matricula) {
        if (repository.findByMatricula(matricula).isPresent()) {
            return repository.deleteByMatricula(matricula);
        }
        return false;
    }


    // ==================== CRUD PARA ENFERMEIROS ======================
    // CREATE
    public Enfermeiro contratarEnfermeiro(EnfermeiroDTO dto) {
        if (repository.existsByMatricula(dto.getMatricula())) {
            throw new IllegalArgumentException("Erro: Matrícula " + dto.getMatricula() + " já está em uso.");
        }
        Enfermeiro enfermeiro = new Enfermeiro(
                dto.getMatricula(), dto.getNome(), dto.getCoren(), dto.getSalarioBase()
        );
        return repository.saveEnfermeiro(enfermeiro);
    }

    //  READ (All)
    public List<Enfermeiro> listarEnfermeiros() {
        return repository.findAllEnfermeiros();
    }

    //  READ (By ID)
    public Optional<Enfermeiro> consultarEnfermeiroPorMatricula(String matricula) {
        return repository.findEnfermeiroByMatricula(matricula);
    }

    //  UPDATE
    public Optional<Enfermeiro> atualizarEnfermeiro(String matricula, EnfermeiroDTO dto) {
        return repository.findEnfermeiroByMatricula(matricula).map(enfermeiro -> {
            enfermeiro.setNome(dto.getNome());
            enfermeiro.setCoren(dto.getCoren());
            enfermeiro.setSalarioBase(dto.getSalarioBase());
            return repository.saveEnfermeiro(enfermeiro);
        });
    }

    // DELETE
    public boolean demitirEnfermeiro(String matricula) {
        // Verifica se quem está sendo demitido é realmente um enfermeiro
        if (repository.findEnfermeiroByMatricula(matricula).isPresent()) {
            return repository.deleteByMatricula(matricula);
        }
        return false;
    }


    // ================== CRUD PARA MÉDICOS EFETIVOS ===================

    // 1. CREATE
    public MedicoEfetivo contratarMedicoEfetivo(MedicoEfetivoDTO dto) {
        if (repository.existsByMatricula(dto.getMatricula())) {
            throw new IllegalArgumentException("Erro: Matrícula " + dto.getMatricula() + " já está em uso.");
        }
        MedicoEfetivo medico = new MedicoEfetivo(
                dto.getMatricula(), dto.getNome(), dto.getCrm(), dto.getSalarioBase()
        );
        return repository.saveMedicoEfetivo(medico);
    }

    // 2. READ (All)
    public List<MedicoEfetivo> listarMedicosEfetivos() {
        return repository.findAllMedicosEfetivos();
    }

    // 3. READ (By ID)
    public Optional<MedicoEfetivo> consultarMedicoEfetivoPorMatricula(String matricula) {
        return repository.findMedicoEfetivoByMatricula(matricula);
    }

    // 4. UPDATE
    public Optional<MedicoEfetivo> atualizarMedicoEfetivo(String matricula, MedicoEfetivoDTO dto) {
        return repository.findMedicoEfetivoByMatricula(matricula).map(medico -> {
            medico.setNome(dto.getNome());
            medico.setCrm(dto.getCrm());
            medico.setSalarioBase(dto.getSalarioBase());
            return repository.saveMedicoEfetivo(medico);
        });
    }

    // 5. DELETE
    public boolean demitirMedicoEfetivo(String matricula) {
        if (repository.findMedicoEfetivoByMatricula(matricula).isPresent()) {
            return repository.deleteByMatricula(matricula);
        }
        return false;
    }


    // =============== CRUD PARA MÉDICOS PLANTONISTAS ==================

    // 1. CREATE
    public MedicoPlantonista contratarMedicoPlantonista(MedicoPlantonistaDTO dto) {
        if (repository.existsByMatricula(dto.getMatricula())) {
            throw new IllegalArgumentException("Erro: Matrícula " + dto.getMatricula() + " já está em uso.");
        }
        MedicoPlantonista medico = new MedicoPlantonista(
                dto.getMatricula(), dto.getNome(), dto.getCrm(), dto.getHorasTrabalhadas(), dto.getValorHoraPlatao()
        );
        return repository.saveMedicoPlantonista(medico);
    }

    // 2. READ (All)
    public List<MedicoPlantonista> listarMedicosPlantonistas() {
        return repository.findAllMedicosPlantonistas();
    }

    // 3. READ (By ID)
    public Optional<MedicoPlantonista> consultarMedicoPlantonistaPorMatricula(String matricula) {
        return repository.findMedicoPlantonistaByMatricula(matricula);
    }

    // 4. UPDATE
    public Optional<MedicoPlantonista> atualizarMedicoPlantonista(String matricula, MedicoPlantonistaDTO dto) {
        return repository.findMedicoPlantonistaByMatricula(matricula).map(medico -> {
            medico.setNome(dto.getNome());
            medico.setCrm(dto.getCrm());
            medico.setHorasTrabalhadas(dto.getHorasTrabalhadas());
            medico.setValorHoraPlatao(dto.getValorHoraPlatao());
            return repository.saveMedicoPlantonista(medico);
        });
    }

    // 5. DELETE
    public boolean demitirMedicoPlantonista(String matricula) {
        if (repository.findMedicoPlantonistaByMatricula(matricula).isPresent()) {
            return repository.deleteByMatricula(matricula);
        }
        return false;
    }





}