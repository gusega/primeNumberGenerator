package com.mytech.controller.inputParser;

import com.mytech.controller.MainController;
import com.mytech.controller.inputModel.ParsedInput;
import com.mytech.generatorservice.PrimeNumberGenerator;

public class ParametersParser {

    /**
     * pre made results for common types of errors
     */
    public final static ParsedInput WRONG_INPUT =
        new ParsedInput(null, 0, 0, ParsedInput.Status.WRONG_FORMAT, null);

    public final static ParsedInput HELP =
        new ParsedInput(null, 0, 0, ParsedInput.Status.HELP, null);

    public final static ParsedInput TEST =
        new ParsedInput(null, 0, 0, ParsedInput.Status.TEST, null);

    public final static ParsedInput WRONG_STRATEGY =
        new ParsedInput(null, 0, 0, ParsedInput.Status.WRONG_STRATEGY, null);


    /**
     * Parses input and returns parsed input to be processed by controller
     *
     * @param args raw input
     * @return parsed input
     */
    public ParsedInput parseParameters(String[] args) {
        if (args.length != 3 && args.length != 1) {
            return WRONG_INPUT;
        }
        if (args.length == 1) {
            if ("--help".equals(args[0])) {
                return HELP;
            }
            if ("test".equals(args[0])) {
                return TEST;
            }
            return WRONG_INPUT;
        }

        PrimeNumberGenerator.Strategies strategy = MainController.inputToStrategies.get(args[0]);
        if (strategy == null) {
            return WRONG_STRATEGY;
        }

        int begin;
        int end;
        try {
            begin = Integer.parseInt(args[1]);
            end = Integer.parseInt(args[2]);

        } catch (NumberFormatException e) {
            return ParsedInput.fromCause(e);
        }

        return new ParsedInput(strategy, begin, end, ParsedInput.Status.OK, null);
    }
}
