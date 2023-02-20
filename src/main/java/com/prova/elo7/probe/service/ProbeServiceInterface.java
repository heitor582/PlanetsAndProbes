package com.prova.elo7.probe.service;

import com.prova.elo7.common.service.interfaces.DeleteServiceInterface;
import com.prova.elo7.common.service.interfaces.FindAllServiceInterface;
import com.prova.elo7.common.service.interfaces.FindByIdServiceInterface;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;

public interface ProbeServiceInterface extends
        FindAllServiceInterface<Probe>, FindByIdServiceInterface<Probe>, DeleteServiceInterface {
    public Probe create(int cordX, int cordY, Direction direction, Long idPlanet, String name);
    public Probe move(Long id,String commands);
    public Probe update(Long id, int cordX, int cordY, Direction direction, Long idPlanet, String name);
}
