package com.prova.elo7.probe.dataproviders.jpa.entities;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "probe")
public class Probe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cord_x")
    private int cordX = 0;

    @Column(name = "cord_y")
    private int cordY = 0;

    private String name = "";

    @OneToOne
    private Planet planet;

    @Enumerated(EnumType.STRING)
    private Direction direction = Direction.UP;

    public void move(String command) {
        if(command.equals("L") || command.equals("R")) {
            this.turn(command);
        } else {
            this.moveForward();
        }
    }

    private void turn(String command) {
        if(command.equals("L")) {
            this.turnLeft();
        }else {
            this.turnRight();
        }
    }

    private void turnRight() {
        if (this.direction == Direction.UP) {
            this.direction = Direction.RIGHT;
        } else if (this.direction == Direction.RIGHT) {
            this.direction = Direction.DOWN;
        } else if (this.direction == Direction.DOWN) {
            this.direction = Direction.LEFT;
        } else {
            this.direction = Direction.UP;
        }
    }

    private void turnLeft() {
        if (this.direction == Direction.UP) {
            this.direction = Direction.LEFT;
        } else if (this.direction == Direction.LEFT) {
            this.direction = Direction.DOWN;
        } else if (this.direction == Direction.DOWN) {
            this.direction = Direction.RIGHT;
        } else {
            this.direction = Direction.UP;
        }
    }

    private void moveForward() {
        if (this.direction == Direction.UP && this.cordY < this.planet.getMaxY()) {
            this.cordY++;
        } else if (this.direction == Direction.LEFT && this.cordX - 1 != 0) {
            this.cordX--;
        } else if (this.direction == Direction.DOWN && this.cordY - 1 != 0) {
            this.cordY--;
        } else if(this.direction == Direction.RIGHT && this.cordX < this.planet.getMaxX()){
            this.cordX++;
        }
    }
}