package com.prova.elo7.planet.entrypoint.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreatePlanetRequest {
    @NotNull
    private int maxY;

    @NotNull
    private int maxX;

    @NotBlank
    private String name;
}
