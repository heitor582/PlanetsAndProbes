package com.prova.elo7.probe.service;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import com.prova.elo7.planet.service.PlanetServiceInterface;
import com.prova.elo7.probe.dataproviders.jpa.ProbeRepository;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.exceptions.ProbeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProbeService implements ProbeServiceInterface {
    private final ProbeRepository probeRepository;
    private final PlanetServiceInterface planetService;

    public Probe create(int cordX, int cordY, Direction direction, Long idPlanet, String name) {
        Probe probe = new Probe();
        probe.setCordX(cordX);
        probe.setCordY(cordY);
        probe.setDirection(direction);
        probe.setName(name);
        Planet planet = planetService.find(idPlanet);
        probe.setPlanet(planet);
        return probeRepository.save(probe);
    }

    public Probe move(Long id, String commands) {
            Probe probe = probeRepository.findById(id)
                    .orElseThrow(() -> new ProbeNotFoundException(id));
            for(String command: commands.split("")){
                probe.move(command);
            }
            return probeRepository.save(probe);
    }

    public Probe info(Long id) {
            return probeRepository.findById(id)
                    .orElseThrow(() -> new ProbeNotFoundException(id));
    }

    public void delete(Long id) {
        Probe probe = probeRepository.findById(id).orElseThrow(() -> new ProbeNotFoundException(id));
        probeRepository.deleteById(probe.getId());
    }

    public List<Probe> findAll() {
        return probeRepository.findAll();
    }
}
