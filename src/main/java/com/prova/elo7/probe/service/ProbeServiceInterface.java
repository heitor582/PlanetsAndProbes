package com.prova.elo7.probe.service;

import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProbeServiceInterface {
    public Probe create(int cordX, int cordY, Direction direction, Long idPlanet, String name);
    public Probe move(Long id,String commands);
    public Probe info(Long id);
    public void delete(Long id);
    public Page<Probe> findAll(Pageable page);
    public Probe update(Long id, int cordX, int cordY, Direction direction, Long idPlanet, String name);
}
