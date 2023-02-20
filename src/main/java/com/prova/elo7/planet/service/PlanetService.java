package com.prova.elo7.planet.service;

import com.prova.elo7.planet.dataproviders.jpa.PlanetRepository;
import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import com.prova.elo7.planet.exceptions.PlanetNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanetService implements PlanetServiceInterface {
    private final PlanetRepository planetRepository;

    public Page<Planet> findAll(Pageable page) {
        return planetRepository.findAll(page);
    }

    public Planet findBy(Long id) {
        return planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
    }

    public Planet create(int maxY, int maxX, String name) {
        Planet planet = new Planet(0L,maxY,maxX,name);

        return planetRepository.save(planet);
    }

    public void delete(Long id) {
        Planet planet = planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException(id));
        planetRepository.deleteById(planet.getId());
    }
}
