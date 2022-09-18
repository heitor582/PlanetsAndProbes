package com.prova.elo7.planet.entrypoint.rest.exceptionHandler;

import com.prova.elo7.probe.exceptions.ProbeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class PlanetControllerAdvice {
    @ExceptionHandler(value = {ProbeNotFoundException.class})
    public ResponseEntity<Object> handlePlanetNotFound(ProbeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
