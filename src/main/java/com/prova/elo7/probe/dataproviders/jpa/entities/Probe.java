package com.prova.elo7.probe.dataproviders.jpa.entities;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "probe")
public final class Probe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cord_x")
    private int cordX = 0;

    @Column(name = "cord_y")
    private int cordY = 0;

    private String name = "";

    @ManyToOne
    private Planet planet;

    @Enumerated(EnumType.STRING)
    private Direction direction = Direction.UP;

    public Probe move(String commands) {
        int tempX = this.getCordX();
        int tempY = this.getCordY();
        Direction tempDirection = this.direction;

        for(String command: commands.split("")) {
            if(command.equals("L")) {
                tempDirection = this.turnLeft(tempDirection);
            } else if (command.equals("R")) {
                tempDirection = this.turnRight(tempDirection);
            } else {
                if(tempDirection == Direction.DOWN || tempDirection == Direction.UP){
                    tempY = moveY(tempY, tempDirection);
                } else {
                    tempX = moveX(tempX, tempDirection);
                }
            }
        }

        return new Probe(this.getId(), tempX, tempY, this.getName(), this.getPlanet(),tempDirection);
    }

    private Direction turnRight(Direction direction) {
        if (direction == Direction.UP) {
            return Direction.RIGHT;
        } else if (direction == Direction.RIGHT) {
            return Direction.DOWN;
        } else if (direction == Direction.DOWN) {
            return Direction.LEFT;
        } else {
            return Direction.UP;
        }
    }

    private Direction turnLeft(Direction direction) {
        if (direction == Direction.UP) {
            return Direction.LEFT;
        } else if (direction == Direction.LEFT) {
            return  Direction.DOWN;
        } else if (direction == Direction.DOWN) {
            return Direction.RIGHT;
        } else {
            return Direction.UP;
        }
    }

    private int moveX(int x, Direction direction) {
        if (direction == Direction.LEFT) {
            return x > -this.planet.getMaxX() ? x - 1 : x * -1;
        } else {
            return x < this.planet.getMaxX() ? x + 1 : x * -1 ;
        }
    }

    private int moveY(int y, Direction direction) {
        if(direction == Direction.UP){
            return y < this.planet.getMaxY() ? y + 1 : y * -1;
        } else {
            return y > -this.planet.getMaxY() ? y - 1 : y * -1;
        }
    }
}