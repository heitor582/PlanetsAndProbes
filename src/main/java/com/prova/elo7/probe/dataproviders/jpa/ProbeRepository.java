package com.prova.elo7.probe.dataproviders.jpa;

import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface ProbeRepository extends JpaRepository<Probe,Long> {
    @Query(nativeQuery = true, value = "select * from probe where planet_id = :planetId")
    public List<Probe> findProbesInSamePlanet(Long planetId);
}
