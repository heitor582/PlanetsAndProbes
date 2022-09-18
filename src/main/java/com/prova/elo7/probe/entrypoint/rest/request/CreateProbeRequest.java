package com.prova.elo7.probe.entrypoint.rest.request;

import com.prova.elo7.probe.dataproviders.jpa.entities.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateProbeRequest {
    @NotNull
    private int cordX;

    @NotNull
    private int cordY;

    @NotNull
    private Long idPlanet;

    @NotNull
    private Direction direction;

    @NotBlank
    private String name;
}
