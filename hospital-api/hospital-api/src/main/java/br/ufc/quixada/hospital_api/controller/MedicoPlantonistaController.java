package br.ufc.quixada.hospital_api.controller;

import br.ufc.quixada.hospital_api.dto.MedicoPlantonistaDTO;
import br.ufc.quixada.hospital_api.model.MedicoPlantonista;
import br.ufc.quixada.hospital_api.service.RHService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicos-plantonistas")
@CrossOrigin(origins = "*")
public class MedicoPlantonistaController {

    @Autowired
    private RHService rhService;

    // CREATE
    @PostMapping
    public ResponseEntity<MedicoPlantonista> contratar(@Valid @RequestBody MedicoPlantonistaDTO dto) {
        MedicoPlantonista novoMedico = rhService.contratarMedicoPlantonista(dto);
        return ResponseEntity.status(201).body(novoMedico);
    }

    // READ (All)
    @GetMapping
    public ResponseEntity<List<MedicoPlantonista>> listar() {
        return ResponseEntity.ok(rhService.listarMedicosPlantonistas());
    }

    // READ (By Matricula)
    @GetMapping("/{matricula}")
    public ResponseEntity<MedicoPlantonista> consultar(@PathVariable String matricula) {
        return rhService.consultarMedicoPlantonistaPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{matricula}")
    public ResponseEntity<MedicoPlantonista> atualizar(@PathVariable String matricula, @Valid @RequestBody MedicoPlantonistaDTO dto) {
        return rhService.atualizarMedicoPlantonista(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> demitir(@PathVariable String matricula) {
        if (rhService.demitirMedicoPlantonista(matricula)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}