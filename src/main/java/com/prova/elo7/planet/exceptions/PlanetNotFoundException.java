package com.prova.elo7.planet.exceptions;

public class PlanetNotFoundException extends RuntimeException {
    public PlanetNotFoundException(Long id) {
        super("Planet not found with id " + id);
    }
}
