package com.prova.elo7.probe.entrypoint.rest;

import com.prova.elo7.probe.dataproviders.jpa.entities.Probe;
import com.prova.elo7.probe.entrypoint.rest.request.CreateProbeRequest;
import com.prova.elo7.probe.entrypoint.rest.request.MoveProbeRequest;
import com.prova.elo7.probe.exceptions.MoveCommandException;
import com.prova.elo7.probe.service.ProbeService;
import com.prova.elo7.probe.service.ProbeServiceInterface;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;

@RestController
@RequestMapping("/probe")
@RequiredArgsConstructor
public class ProbeResource {
    private final ProbeServiceInterface probeService;

    @PostMapping
    @Operation(tags = {"Probe"}, summary = "Create a probe and associate a planet by id")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<Probe> create(@Valid @RequestBody CreateProbeRequest createProbeRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(probeService.create(
                createProbeRequest.getCordX(),
                createProbeRequest.getCordY(),
                createProbeRequest.getDirection(),
                createProbeRequest.getIdPlanet(),
                createProbeRequest.getName()
        ));
    }

    @PostMapping("/{id}/move")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(tags = {"Probe"}, summary = "Move the probe around planet")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<Probe> move(@PathVariable Long id,  @Valid @RequestBody MoveProbeRequest moveProbeRequest) {
        if(!moveProbeRequest.getCommand().matches("ˆ[LMR]*$")) {
            throw new MoveCommandException();
        }
        return ResponseEntity.ok(probeService.move(id, moveProbeRequest.getCommand()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(tags = {"Probe"}, summary = "Get probe info by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<Probe> info(@PathVariable Long id) {
        return ResponseEntity.ok(probeService.info(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(tags = {"Probe"}, summary = "Delete a probe by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<String> delete(@PathVariable Long id) {
        probeService.delete(id);
        return ResponseEntity.ok("Probe successfully deleted with id " + id);
    }
}
