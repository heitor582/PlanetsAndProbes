package com.prova.elo7.probe.service;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import com.prova.elo7.planet.service.PlanetServiceInterface;
import com.prova.elo7.probe.dataproviders.jpa.ProbeRepository;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.exceptions.ProbeLandingException;
import com.prova.elo7.probe.exceptions.ProbeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProbeService implements ProbeServiceInterface {
    private final ProbeRepository probeRepository;
    private final PlanetServiceInterface planetService;

    public Probe create(int cordX, int cordY, Direction direction, Long idPlanet, String name) {
        Planet planet = planetService.find(idPlanet);
        if(Math.abs(cordX) > planet.getMaxX() || Math.abs(cordY) > planet.getMaxY()) {
            throw new ProbeLandingException();
        }

        Probe probe = new Probe(0L, cordX, cordY, name, planet, direction);

        return probeRepository.save(probe);
    }

    public Probe move(Long id, String commands) {
        Probe probe = probeRepository.findById(id)
                    .orElseThrow(() -> new ProbeNotFoundException(id));
        List<Probe> probes = probeRepository.findProbesInSamePlanet(probe.getPlanet().getId());
        List<Integer> xAxes = probes.stream().map(Probe::getCordX).collect(Collectors.toList());
        List<Integer> yAxes = probes.stream().map(Probe::getCordY).collect(Collectors.toList());
        return probeRepository.save(probe.move(commands, xAxes, yAxes));
    }

    public Probe info(Long id) {
            return probeRepository.findById(id)
                    .orElseThrow(() -> new ProbeNotFoundException(id));
    }

    public void delete(Long id) {
        Probe probe = probeRepository.findById(id).orElseThrow(() -> new ProbeNotFoundException(id));
        probeRepository.deleteById(probe.getId());
    }

    public Page<Probe> findAll(Pageable page) {
        return probeRepository.findAll(page);
    }

    public Probe update(Long id, int cordX, int cordY, Direction direction, Long idPlanet, String name) {
        Probe probe = probeRepository.findById(id)
                .orElseThrow(() -> new ProbeNotFoundException(id));
        Planet planet = planetService.find(idPlanet);
        if(Math.abs(cordX) > planet.getMaxX() || Math.abs(cordY) > planet.getMaxY()) {
            throw new ProbeLandingException();
        }
        return probeRepository.save(new Probe(probe.getId(), cordX, cordY, name, planet, direction));
    }
}
