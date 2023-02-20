package com.prova.elo7.common.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllServiceInterface<T> {
    public Page<T> findAll(Pageable page);
}
