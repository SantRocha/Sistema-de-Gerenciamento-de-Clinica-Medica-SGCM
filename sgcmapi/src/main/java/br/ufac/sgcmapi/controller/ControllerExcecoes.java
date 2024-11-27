package br.ufac.sgcmapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExcecoes {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> validation(MethodArgumentNotValidException e) {
        Map<String, List<String>> resposta = new HashMap<>();
        List<String> mensagensErro = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(
            error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                String mensagem = fieldName + " " + errorMessage;
                mensagensErro.add(mensagem);
            }
        );
        resposta.put("message", mensagensErro);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
    
}
