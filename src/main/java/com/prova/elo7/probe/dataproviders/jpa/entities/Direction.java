package com.prova.elo7.probe.dataproviders.jpa.entities;

public enum Direction {
    UP {
        @Override
        public Direction turnLeft() {
            return LEFT;
        }

        @Override
        public Direction turnRight() {
            return RIGHT;
        }
    },
    DOWN {
        @Override
        public Direction turnLeft() {
            return RIGHT;
        }

        @Override
        public Direction turnRight() {
            return LEFT;
        }
    },
    LEFT {
        @Override
        public Direction turnLeft() {
            return DOWN;
        }

        @Override
        public Direction turnRight() {
            return UP;
        }
    },
    RIGHT {
        @Override
        public Direction turnLeft() {
            return UP;
        }

        @Override
        public Direction turnRight() {
            return DOWN;
        }
    };

    public abstract Direction turnLeft();
    public abstract Direction turnRight();
}
