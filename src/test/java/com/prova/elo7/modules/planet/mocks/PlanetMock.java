package com.prova.elo7.modules.planet.mocks;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;

public class PlanetMock {
    public static Planet createPlanet(Long id, int maxY, int maxX, String name) {
        return new Planet(id, maxY, maxX, name);
    }
}
