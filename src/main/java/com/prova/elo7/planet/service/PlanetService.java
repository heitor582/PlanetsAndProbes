package com.prova.elo7.planet.service;

import com.prova.elo7.planet.dataproviders.jpa.PlanetRepository;
import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import com.prova.elo7.planet.exceptions.PlanetNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanetService implements PlanetServiceInterface {
    private final PlanetRepository planetRepository;

    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    public Planet find(Long id) {
        return planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
    }

    public Planet create(int maxY, int maxX, String name) {
        Planet planet = new Planet();
        planet.setMaxX(maxX);
        planet.setMaxY(maxY);
        planet.setName(name);

        return planetRepository.save(planet);
    }

    public void delete(Long id) {
        Planet planet = planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
        planetRepository.deleteById(planet.getId());
    }
}
