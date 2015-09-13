package com.mytech.controller.inputModel;

import com.mytech.generatorservice.PrimeNumberGenerator;

public class ParsedInput {
    private final PrimeNumberGenerator.Strategies strategy;
    private final int begin;
    private final int end;
    private final Status status;
    private final Throwable cause;

    public PrimeNumberGenerator.Strategies getStrategy() {
        return strategy;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public Status getStatus() {
        return status;
    }

    public Throwable getCause() {
        return cause;
    }

    public ParsedInput(PrimeNumberGenerator.Strategies strategy, int begin, int end,
        Status status, Throwable cause) {
        this.strategy = strategy;
        this.begin = begin;
        this.end = end;
        this.status = status;
        this.cause = cause;
    }

    static ParsedInput wrongInput() {
        return new ParsedInput(null, 0, 0, Status.WRONG_FORMAT, null);
    }

    public static ParsedInput fromCause(Throwable cause) {
        return new ParsedInput(null, 0, 0, Status.WRONG_FORMAT, cause);
    }

    public enum Status {
        WRONG_FORMAT, HELP, TEST, WRONG_STRATEGY, OK
    }
}
