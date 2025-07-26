package br.ufc.quixada.hospital_api.controller;

import br.ufc.quixada.hospital_api.dto.EnfermeiroDTO;
import br.ufc.quixada.hospital_api.model.Enfermeiro;
import br.ufc.quixada.hospital_api.service.RHService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enfermeiros")
@CrossOrigin(origins = "*")
public class EnfermeiroController {

    @Autowired
    private RHService rhService;

    // CREATE
    @PostMapping
    public ResponseEntity<Enfermeiro> contratar(@Valid @RequestBody EnfermeiroDTO dto) {
        Enfermeiro novoEnfermeiro = rhService.contratarEnfermeiro(dto);
        return ResponseEntity.status(201).body(novoEnfermeiro);
    }

    // READ (All)
    @GetMapping
    public ResponseEntity<List<Enfermeiro>> listar() {
        return ResponseEntity.ok(rhService.listarEnfermeiros());
    }

    // READ (By Matricula)
    @GetMapping("/{matricula}")
    public ResponseEntity<Enfermeiro> consultar(@PathVariable String matricula) {
        return rhService.consultarEnfermeiroPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{matricula}")
    public ResponseEntity<Enfermeiro> atualizar(@PathVariable String matricula, @Valid @RequestBody EnfermeiroDTO dto) {
        return rhService.atualizarEnfermeiro(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> demitir(@PathVariable String matricula) {
        if (rhService.demitirEnfermeiro(matricula)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}