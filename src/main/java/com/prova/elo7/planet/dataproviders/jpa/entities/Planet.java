package com.prova.elo7.planet.dataproviders.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "max_y")
    private int maxY = 0;
    @Column(name = "max_x")
    private int maxX = 0;
    private String name = "";
}
