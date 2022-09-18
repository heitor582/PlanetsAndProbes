package com.prova.elo7.modules.probe.dataproviders.jpa.entities;

import com.prova.elo7.modules.probe.mocks.ProbeMock;
import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProbeTest {
    @Nested
    class MoveProbe {
        @ParameterizedTest
        @MethodSource("provideProbeInitAndFinal")
        @DisplayName("Move probe")
        void moveProbe(int initX, int initY, Direction initDirection, int finalX, int finalY, Direction finalDirection, String command) {
            Probe probe = ProbeMock.createProbe(1L, initX, initY, initDirection);
            probe.move(command);

            assertThat(probe.getCordX()).isEqualTo(finalX);
            assertThat(probe.getCordY()).isEqualTo(finalY);
            assertThat(probe.getDirection()).isEqualTo(finalDirection);
        }

        private static Stream<Arguments> provideProbeInitAndFinal() {
            return Stream.of(
                    Arguments.of(1, 2, Direction.UP, 1, 3, Direction.UP, "M"),
                    Arguments.of(1, 2, Direction.UP, 1, 2, Direction.RIGHT, "R"),
                    Arguments.of(1, 2, Direction.UP, 1, 2, Direction.LEFT, "L"),
                    Arguments.of(1, 2, Direction.LEFT, 1, 2, Direction.LEFT, "M"),

                    Arguments.of(1, 2, Direction.DOWN, 1, 2, Direction.RIGHT, "L"),
                    Arguments.of(1, 2, Direction.DOWN, 1, 2, Direction.LEFT, "R"),
                    Arguments.of(1, 2, Direction.DOWN, 1, 1, Direction.DOWN, "M"),
                    Arguments.of(1,1, Direction.DOWN, 1, 1, Direction.DOWN, "M"),

                    Arguments.of(2, 2, Direction.LEFT, 1, 2, Direction.LEFT, "M"),
                    Arguments.of(1, 2, Direction.LEFT, 1, 2, Direction.DOWN, "L"),
                    Arguments.of(1, 2, Direction.LEFT, 1, 2, Direction.UP, "R"),
                    Arguments.of(1, 2, Direction.LEFT, 1, 2, Direction.LEFT, "M"),

                    Arguments.of(1, 2, Direction.RIGHT, 2, 2, Direction.RIGHT, "M"),
                    Arguments.of(1, 2, Direction.RIGHT, 1, 2, Direction.UP, "L"),
                    Arguments.of(1, 2, Direction.RIGHT, 1, 2, Direction.DOWN, "R"),
                    Arguments.of(5, 2, Direction.RIGHT, 5, 2, Direction.RIGHT, "M")

            );
        }
    }

}
