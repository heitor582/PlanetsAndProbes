package com.prova.elo7.planet.dataproviders.jpa;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet,Long> {}
