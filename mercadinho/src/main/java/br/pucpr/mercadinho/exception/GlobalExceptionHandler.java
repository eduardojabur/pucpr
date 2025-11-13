package br.pucpr.mercadinho.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {

        ErrorResponse error = new ErrorResponse(
                400,
                ex.getCodeDescription(),
                ex.getMessage(),
                ex.getCause() != null ? ex.getCause().getMessage() : "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidExceptions(MethodArgumentNotValidException ex) {

        // Pega a primeira mensagem de erro de validação (ex: "A senha deve ter no mínimo 6 caracteres")
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst() // Pega o primeiro erro encontrado
                .orElse("Erro de validação"); // Mensagem padrão se nenhuma for encontrada

        ErrorResponse error = new ErrorResponse(
                400,
                "VALIDATION_ERROR", // Código de erro genérico
                errorMessage, // A mensagem de erro real
                "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        // Corrigido para não quebrar se ex.getCause() for nulo
        String stackTrack = ex.getCause() != null ? ex.getCause().toString() : ex.getMessage();

        ErrorResponse error = new ErrorResponse(
                500,
                "INTERNAL_SERVER_ERROR",
                "Ocorreu um erro inesperado",
                stackTrack,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}