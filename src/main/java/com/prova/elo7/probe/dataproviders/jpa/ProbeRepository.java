package com.prova.elo7.probe.dataproviders.jpa;

import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface ProbeRepository extends JpaRepository<Probe,Long> {
}
