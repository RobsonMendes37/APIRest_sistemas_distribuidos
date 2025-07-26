package br.ufc.quixada.hospital_api.exception;

import br.ufc.quixada.hospital_api.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice // Marca a classe como tratador de exceções global
public class RestExceptionHandler {

    //Trata erros de validação do @Valid (campos em branco, etc.).
    //Retorna um erro 400 Bad Request com uma lista de mensagens.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                errors,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //Trata erros de negócio, como matrícula duplicada.
    //Retorna um erro 400 Bad Request.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Inválida",
                Collections.singletonList(ex.getMessage()), // Cria uma lista com uma única mensagem
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //trata qualquer outra exceção não esperada.
    // Retorna um erro 500 Internal Server Error, escondendo detalhes do sistema.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                Collections.singletonList("Ocorreu um erro inesperado. Tente novamente mais tarde."),
                request.getRequestURI()
        );

        // Logar o erro real no console do servidor para depuração
        ex.printStackTrace();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}