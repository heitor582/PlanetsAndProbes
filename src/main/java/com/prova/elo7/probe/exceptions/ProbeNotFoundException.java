package com.prova.elo7.probe.exceptions;

public class ProbeNotFoundException extends RuntimeException {
    public ProbeNotFoundException(Long id) {
        super("Probe not found with id " + id);
    }
}
