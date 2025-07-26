package br.ufc.quixada.hospital_api.controller;

import br.ufc.quixada.hospital_api.model.Funcionario;
import br.ufc.quixada.hospital_api.service.RHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/rh") // Rota base para operações gerais de RH
@CrossOrigin(origins = "*")
public class RHController {

    @Autowired
    private RHService rhService;


    @GetMapping("/hospital")
    public String getNomeHospital() {
        return rhService.getNomeHospital();
    }


    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> listarTodos() {
        return ResponseEntity.ok(rhService.listarTodosFuncionarios());
    }


    @GetMapping("/funcionarios/{matricula}")
    public ResponseEntity<Funcionario> consultar(@PathVariable String matricula) {
        return rhService.consultarFuncionario(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/funcionarios/{matricula}")
    public ResponseEntity<Void> demitir(@PathVariable String matricula) {
        if (rhService.demitirFuncionario(matricula)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}