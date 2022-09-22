package com.prova.elo7.probe.exceptions;

public class ProbeLandingException extends RuntimeException {
    public ProbeLandingException() {
        super("Probe landing out of bound on x or y axis");
    }
}
