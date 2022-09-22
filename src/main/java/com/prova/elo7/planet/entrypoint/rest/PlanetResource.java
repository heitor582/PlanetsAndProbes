package com.prova.elo7.planet.entrypoint.rest;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import com.prova.elo7.planet.entrypoint.rest.request.CreatePlanetRequest;
import com.prova.elo7.planet.service.PlanetServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planet")
@RequiredArgsConstructor
public class PlanetResource {
    private final PlanetServiceInterface planetService;

    @GetMapping
    @Operation(tags = {"Planet"}, summary = "Find all planets")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "204"),
            }
    )

    ResponseEntity<Page<Planet>> findAll(@PageableDefault(
            page = 0,
            size = 10,
            sort = {"id"}) Pageable page) {
        Page<Planet> planets = planetService.findAll(page);
        return planets.getContent().size() > 0 ? ResponseEntity.ok(planets) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(tags = {"Planet"}, summary = "Create a planet")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<Planet> create(@Valid @RequestBody CreatePlanetRequest createPlanetRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.create(
                createPlanetRequest.getMaxY(),
                createPlanetRequest.getMaxX(),
                createPlanetRequest.getName()
        ));
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(tags = {"Planet"}, summary = "Find a planet by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<Planet> find(@PathVariable Long id) {
        return ResponseEntity.ok(planetService.find(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(tags = {"Planet"}, summary = "Delete a planet by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    ResponseEntity<String> delete(@PathVariable Long id) {
        planetService.delete(id);
        return ResponseEntity.ok("Planet successfully deleted with id " + id);
    }
}
