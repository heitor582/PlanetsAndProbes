package com.prova.elo7.probe.entrypoint.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveProbeRequest {

    @NotEmpty
    private String command;
}
