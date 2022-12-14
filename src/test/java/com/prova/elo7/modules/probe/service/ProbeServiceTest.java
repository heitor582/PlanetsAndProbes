package com.prova.elo7.modules.probe.service;

import com.prova.elo7.modules.probe.mocks.ProbeMock;
import com.prova.elo7.planet.exceptions.PlanetNotFoundException;
import com.prova.elo7.planet.service.PlanetServiceInterface;
import com.prova.elo7.probe.dataproviders.jpa.ProbeRepository;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import com.prova.elo7.probe.exceptions.ProbeLandingException;
import com.prova.elo7.probe.exceptions.ProbeNotFoundException;
import com.prova.elo7.probe.service.ProbeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProbeServiceTest {

    @Mock
    PlanetServiceInterface planetServiceInterface;

    @Mock
    ProbeRepository probeRepository;

    @InjectMocks
    ProbeService probeService;

    @Nested
    class CreateProbe {
        @Test
        @DisplayName("Create Probe")
        void createProbe() {
            Probe probe = ProbeMock.createProbe(1L,1,2, Direction.UP);
            given(planetServiceInterface.find(any())).willReturn(probe.getPlanet());
            given(probeRepository.save(any())).willReturn(probe);
            Probe createdProbe = probeService.create(
              probe.getCordX(),
              probe.getCordY(),
              probe.getDirection(),
              probe.getPlanet().getId(),
              probe.getName()
            );

            assertThat(createdProbe).isEqualTo(probe);
        }

        @Test
        @DisplayName("Not create Probe when planet not exists")
        void notCreateProbe() {
            Probe probe = ProbeMock.createProbe(1L,1,2, Direction.UP);
            given(planetServiceInterface.find(any())).willThrow(PlanetNotFoundException.class);
            assertThatThrownBy(() -> probeService.create(
                    probe.getCordX(),
                    probe.getCordY(),
                    probe.getDirection(),
                    5L,
                    probe.getName()
            )).isInstanceOf(PlanetNotFoundException.class);
        }

        @Test
        @DisplayName("Not create Probe when try to landing out of bound")
        void notCreateProbeOutOfBound() {
            Probe probe = ProbeMock.createProbe(1L,5,-7, Direction.UP);
            given(planetServiceInterface.find(any())).willReturn(probe.getPlanet());
            assertThatThrownBy(() -> probeService.create(
                    probe.getCordX(),
                    probe.getCordY(),
                    probe.getDirection(),
                    probe.getPlanet().getId(),
                    probe.getName()
            )).isInstanceOf(ProbeLandingException.class);
        }
    }

    @Nested
    class DeleteProbe{
        @Test
        @DisplayName("Delete probe")
        void deleteProbe() {
            Probe probe = ProbeMock.createProbe(1L, 1, 2, Direction.UP);
            given(probeRepository.findById(any())).willReturn(Optional.of(probe));
            assertThatCode(() -> probeService.delete(probe.getId())).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Throws error when delete probe")
        void notDeleteProbe() {
            given(probeRepository.findById(any())).willReturn(Optional.empty());
            assertThatThrownBy(() -> probeService.delete(any())).isInstanceOf(ProbeNotFoundException.class);
        }
    }

    @Nested
    class FindProbe {
        @Test
        @DisplayName("Find probe by id")
        void findProbeById() {
            Probe probe = ProbeMock.createProbe(1L,1,2, Direction.UP);
            given(probeRepository.findById(probe.getId())).willReturn(Optional.of(probe));
            Probe foundProbe = probeService.info(probe.getId());
            assertThat(foundProbe).isEqualTo(foundProbe);
        }

        @Test
        @DisplayName("Not find probe by id")
        void notFindProbeById() {
            given(probeRepository.findById(any())).willReturn(Optional.empty());
            assertThatThrownBy(() -> probeService.info(any())).isInstanceOf(ProbeNotFoundException.class);
        }

        @Test
        @DisplayName("Find all probes")
        void findAll() {
            Pageable page = PageRequest.of(0,10);
            Probe probe1 = ProbeMock.createProbe(1L,1,2, Direction.UP);
            Probe probe2 = ProbeMock.createProbe(1L,1,2, Direction.UP);
            given(probeRepository.findAll(page)).willReturn(new PageImpl<Probe>(List.of(probe1,probe2)));
            Page<Probe> probes = probeService.findAll(page);
            assertThat(probes).isEqualTo(new PageImpl<Probe>(List.of(probe1,probe2)));
        }
    }

    @Nested
    class MoveProbe {
        @ParameterizedTest
        @MethodSource("provideProbeInitAndFinal")
        @DisplayName("Move probe")
        void moveProbe(int initX, int initY, Direction initDirection, int finalX, int finalY, Direction finalDirection, String commands) {
            Probe probe = ProbeMock.createProbe(1L, initX, initY, initDirection);
            given(probeRepository.findById(probe.getId())).willReturn(Optional.of(probe));
            given(probeRepository.save(any())).willReturn(probe.move(commands, List.of(), List.of()));
            Probe movedProbe = probeService.move(probe.getId(), commands);

            assertThat(movedProbe.getCordX()).isEqualTo(finalX);
            assertThat(movedProbe.getCordY()).isEqualTo(finalY);
            assertThat(movedProbe.getDirection()).isEqualTo(finalDirection);
        }

        @Test
        @DisplayName("Not move a nonexistent probe")
        void notFindProbeById() {
            given(probeRepository.findById(any())).willReturn(Optional.empty());
            assertThatThrownBy(() -> probeService.move(any(), "LMLMLMLMM")).isInstanceOf(ProbeNotFoundException.class);
        }

        @Test
        @DisplayName("Move with other probes in the same planet")
        void moveWithOtherProbes() {
            Probe probe = ProbeMock.createProbe(1L, 1, 2, Direction.UP);
            String commands = "LLMMR";
            given(probeRepository.findById(probe.getId())).willReturn(Optional.of(probe));
            given(probeRepository.findProbesInSamePlanet(probe.getPlanet().getId())).willReturn(List.of(ProbeMock.createProbe(2L, 1, 1, Direction.UP)));
            given(probeRepository.save(any())).willReturn(probe.move(commands, List.of(1), List.of(1)));
            Probe movedProbe = probeService.move(probe.getId(), commands);

            assertThat(movedProbe.getCordX()).isEqualTo(1);
            assertThat(movedProbe.getCordY()).isEqualTo(2);
            assertThat(movedProbe.getDirection()).isEqualTo(Direction.LEFT);
        }

        private static Stream<Arguments> provideProbeInitAndFinal() {
            return Stream.of(
                    Arguments.of(1, 2, Direction.UP, 1, 3, Direction.UP, "LMLMLMLMM"),
                    Arguments.of(3, 3, Direction.RIGHT, 5, 1, Direction.UP, "MMRMMRMRRML")
            );
        }
    }

    @Nested
    class UpdateProbe {
        @Test
        @DisplayName("Update probe by id")
        void updateProbe() {
            Probe probe = ProbeMock.createProbe(1L,1,2, Direction.UP);
            given(planetServiceInterface.find(any())).willReturn(probe.getPlanet());
            given(probeRepository.findById(probe.getId())).willReturn(Optional.of(probe));
            given(probeRepository.save(any())).willReturn(new Probe(probe.getId(), 3, 4, probe.getName(),probe.getPlanet(), Direction.RIGHT));

           Probe updatedProbe = probeService.update(
              probe.getId(),
              3,
              4,
              Direction.RIGHT,
                    probe.getPlanet().getId(),
                    probe.getName()
            );
            assertThat(updatedProbe.getCordY()).isEqualTo(4);
            assertThat(updatedProbe.getCordX()).isEqualTo(3);
            assertThat(updatedProbe.getDirection()).isEqualTo(Direction.RIGHT);
        }
    }
}
