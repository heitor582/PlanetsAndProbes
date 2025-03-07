package com.prova.elo7.planet.dataproviders.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planet")
public final class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "max_y")
    private int maxY = 0;

    @Column(name = "max_x")
    private int maxX = 0;

    private String name = "";
}
