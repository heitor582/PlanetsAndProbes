package com.prova.elo7.probe.dataproviders.jpa.entities;

import com.prova.elo7.planet.dataproviders.jpa.entities.Planet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    public Probe move(String commands, List<Integer> xAxes, List<Integer> yAxes) {
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
                    tempY = moveY(tempY, tempDirection, yAxes);
                } else {
                    tempX = moveX(tempX, tempDirection, xAxes);
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

    private int moveX(int x, Direction direction, List<Integer> xAxes) {
        int tempX = 0;
        if (direction == Direction.LEFT) {
            tempX =  x > -this.planet.getMaxX() ? x - 1 : x * -1;
        } else {
            tempX =  x < this.planet.getMaxX() ? x + 1 : x * -1 ;
        }

        return xAxes.contains(x) ? x : tempX;
    }

    private int moveY(int y, Direction direction, List<Integer> yAxes) {
        int tempY = 0;
        if(direction == Direction.UP){
            tempY = y < this.planet.getMaxY() ? y + 1 : y * -1;
        } else {
            tempY =  y > -this.planet.getMaxY() ? y - 1 : y * -1;
        }

        return yAxes.contains(tempY) ? y : tempY;
    }
}