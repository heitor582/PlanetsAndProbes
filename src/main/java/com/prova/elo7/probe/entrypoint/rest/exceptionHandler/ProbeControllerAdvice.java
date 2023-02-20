package com.prova.elo7.probe.entrypoint.rest.exceptionHandler;

import com.prova.elo7.probe.exceptions.MoveCommandException;
import com.prova.elo7.probe.exceptions.ProbeLandingException;
import com.prova.elo7.probe.exceptions.ProbeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ProbeControllerAdvice {
    @ExceptionHandler(value = {ProbeNotFoundException.class})
    public ResponseEntity<Object> handleProbeNotFound(ProbeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = {MoveCommandException.class, ProbeLandingException.class})
    public ResponseEntity<Object> handleWrongMoveCommand(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
