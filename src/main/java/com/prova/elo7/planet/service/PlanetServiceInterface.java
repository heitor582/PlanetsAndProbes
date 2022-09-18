package com.prova.elo7.planet.service;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;

import java.util.List;

public interface PlanetServiceInterface {
    public List<Planet> findAll();
    public Planet find(Long id);
    public Planet create(int maxY, int maxX, String name);
    public void delete(Long id);
}
