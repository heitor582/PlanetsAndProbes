package com.prova.elo7.planet.service;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanetServiceInterface {
    public Page<Planet> findAll(Pageable page);
    public Planet find(Long id);
    public Planet create(int maxY, int maxX, String name);
    public void delete(Long id);
}
