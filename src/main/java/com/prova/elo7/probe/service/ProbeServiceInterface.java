package com.prova.elo7.probe.service;

import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;

public interface ProbeServiceInterface {
    public Probe create(int cordX, int cordY, Direction direction, Long idPlanet, String name);
    public Probe move(Long id,String commands);
    public Probe info(Long id);
    public void delete(Long id);
}
