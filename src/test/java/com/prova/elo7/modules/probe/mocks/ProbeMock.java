package com.prova.elo7.modules.probe.mocks;

import com.prova.elo7.modules.planet.mocks.PlanetMock;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;

public class ProbeMock {
    public static Probe createProbe(Long id, int x, int y, Direction direction) {
        return new Probe(id, x, y, "Test", PlanetMock.createPlanet(1L, 5, 5, "Planet"), direction);
    }
}
