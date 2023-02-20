package com.prova.elo7.planet.service;

import com.prova.elo7.common.service.interfaces.DeleteServiceInterface;
import com.prova.elo7.common.service.interfaces.FindAllServiceInterface;
import com.prova.elo7.common.service.interfaces.FindByIdServiceInterface;
import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;

public interface PlanetServiceInterface
        extends FindAllServiceInterface<Planet>, FindByIdServiceInterface<Planet>, DeleteServiceInterface {
    public Planet create(int maxY, int maxX, String name);
}
