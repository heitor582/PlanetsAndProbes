package com.prova.elo7.common.service.interfaces;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;

public interface FindByIdServiceInterface<T> {
    public T findBy(Long id);
}
