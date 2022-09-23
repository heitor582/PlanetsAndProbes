package com.prova.elo7.modules.planet.service;

import com.prova.elo7.modules.planet.mocks.PlanetMock;
import com.prova.elo7.planet.dataproviders.jpa.PlanetRepository;
import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import com.prova.elo7.planet.exceptions.PlanetNotFoundException;
import com.prova.elo7.planet.service.PlanetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {
    @Mock
    PlanetRepository planetRepository;

    @InjectMocks
    PlanetService planetService;

    @Nested
    class CreatePlanet {
        @Test
        @DisplayName("Should create a planet")
        void createPlanet() {
            Planet planet = PlanetMock.createPlanet(1L, 5, 5, "Teste");
            given(planetRepository.save(any())).willReturn(planet);
            Planet createdPlanet = planetService.create(
                    planet.getMaxY(),
                    planet.getMaxX(),
                    planet.getName()
            );
            assertThat(createdPlanet).isEqualTo(planet);
        }
    }

    @Nested
    class FindPlanet {
        @Test
        @DisplayName("Find planet by id")
        void findPlanet() {
            Planet planet = PlanetMock.createPlanet(1L, 5, 5, "Teste");
            given(planetRepository.findById(planet.getId())).willReturn(Optional.of(planet));
            Planet planetFound = planetService.find(planet.getId());
            assertThat(planetFound).isEqualTo(planet);
        }

        @Test
        @DisplayName("Not find planet by id")
        void notFindPlanet() {
            Planet planet = PlanetMock.createPlanet(1L, 5, 5, "Teste");
            given(planetRepository.findById(planet.getId())).willReturn(Optional.empty());
            assertThatThrownBy(() -> planetService.find(planet.getId())).isInstanceOf(PlanetNotFoundException.class);
        }

        @Test
        @DisplayName("Find all planets")
        void findAllPlanets() {
            Pageable page = PageRequest.of(0,10);
            Planet planet1 = PlanetMock.createPlanet(1L, 5, 5, "Teste");
            Planet planet2 = PlanetMock.createPlanet(2L, 5, 5, "Teste2");
            given(planetRepository.findAll(page)).willReturn(new PageImpl<Planet>(List.of(planet1,planet2)));
            Page<Planet> planetsFound = planetService.findAll(page);
            assertThat(planetsFound).isEqualTo(new PageImpl<Planet>(List.of(planet1,planet2)));
        }
    }

    @Nested
    class DeletePlanet {
        @Test
        @DisplayName("Delete planet by id")
        void deletePlanet() {
            Planet planet = PlanetMock.createPlanet(1L, 5, 5, "Teste");
            given(planetRepository.findById(planet.getId())).willReturn(Optional.of(planet));
            assertThatCode(() -> planetService.delete(planet.getId())).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Throws error when delete planet")
        void notDeletePlanet() {
            given(planetRepository.findById(any())).willThrow(PlanetNotFoundException.class);
            assertThatThrownBy(() -> planetService.delete(any())).isInstanceOf(PlanetNotFoundException.class);
        }
    }
}
