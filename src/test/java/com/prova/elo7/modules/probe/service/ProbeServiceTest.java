package com.prova.elo7.modules.probe.service;

import com.prova.elo7.modules.probe.mocks.ProbeMock;
import com.prova.elo7.planet.exceptions.PlanetNotFoundException;
import com.prova.elo7.planet.service.PlanetServiceInterface;
import com.prova.elo7.probe.dataproviders.jpa.ProbeRepository;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
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
            Probe probe1 = ProbeMock.createProbe(1L,1,2, Direction.UP);
            Probe probe2 = ProbeMock.createProbe(1L,1,2, Direction.UP);
            given(probeRepository.findAll()).willReturn(List.of(probe1,probe2));
            List<Probe> probes = probeService.findAll();
            assertThat(probes).isEqualTo(List.of(probe1,probe2));
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
            Probe probeAfterMoved = probeService.move(probe.getId(), commands);

            assertThat(probe.getCordX()).isEqualTo(finalX);
            assertThat(probe.getCordY()).isEqualTo(finalY);
            assertThat(probe.getDirection()).isEqualTo(finalDirection);
        }

        @Test
        @DisplayName("Not move a nonexistent probe")
        void notFindProbeById() {
            given(probeRepository.findById(any())).willReturn(Optional.empty());
            assertThatThrownBy(() -> probeService.move(any(), "LMLMLMLMM")).isInstanceOf(ProbeNotFoundException.class);
        }

        private static Stream<Arguments> provideProbeInitAndFinal() {
            return Stream.of(
                    Arguments.of(1, 2, Direction.UP, 1, 3, Direction.UP, "LMLMLMLMM"),
                    Arguments.of(3, 3, Direction.RIGHT, 5, 1, Direction.UP, "MMRMMRMRRML")
            );
        }
    }
}
