package com.prova.elo7.probe.exceptions;

public class MoveCommandException  extends RuntimeException {
    public MoveCommandException() {super("the move command contains a wrong character, only L M R allowed");}
}
