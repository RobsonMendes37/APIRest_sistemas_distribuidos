package br.ufc.quixada.hospital_api.controller;

import br.ufc.quixada.hospital_api.dto.MedicoEfetivoDTO;
import br.ufc.quixada.hospital_api.model.MedicoEfetivo;
import br.ufc.quixada.hospital_api.service.RHService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicos-efetivos")
@CrossOrigin(origins = "*")
public class MedicoEfetivoController {

    @Autowired
    private RHService rhService;

    // CREATE
    @PostMapping
    public ResponseEntity<MedicoEfetivo> contratar(@Valid @RequestBody MedicoEfetivoDTO dto) {
        MedicoEfetivo novoMedico = rhService.contratarMedicoEfetivo(dto);
        return ResponseEntity.status(201).body(novoMedico);
    }

    // READ (All)
    @GetMapping
    public ResponseEntity<List<MedicoEfetivo>> listar() {
        return ResponseEntity.ok(rhService.listarMedicosEfetivos());
    }

    // READ (By Matricula)
    @GetMapping("/{matricula}")
    public ResponseEntity<MedicoEfetivo> consultar(@PathVariable String matricula) {
        return rhService.consultarMedicoEfetivoPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{matricula}")
    public ResponseEntity<MedicoEfetivo> atualizar(@PathVariable String matricula, @Valid @RequestBody MedicoEfetivoDTO dto) {
        return rhService.atualizarMedicoEfetivo(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> demitir(@PathVariable String matricula) {
        if (rhService.demitirMedicoEfetivo(matricula)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}