package com.mytech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mytech.controller.inputModel.ParsedInput;
import com.mytech.controller.inputParser.ParametersParser;
import com.mytech.generatorservice.PrimeNumberGenerator;

public class MainController {
    private PrimeNumberGenerator generator;// = new PrimeNumberGenerator();
    private ParametersParser parametersParser;


    public MainController(PrimeNumberGenerator generator,
        ParametersParser parametersParser) {
        this.generator = generator;
        this.parametersParser = parametersParser;
    }

    public MainController() {
        this.generator = new PrimeNumberGenerator();
        this.parametersParser = new ParametersParser();
    }

    public static Map<String, PrimeNumberGenerator.Strategies> inputToStrategies = new HashMap<>();

    static {
        inputToStrategies.put("n", PrimeNumberGenerator.Strategies.NO_OPTIMISATION);
        inputToStrategies.put("e", PrimeNumberGenerator.Strategies.FILTER_OUT_EVEN_AND_5MULTIPLES);
        inputToStrategies.put("o", PrimeNumberGenerator.Strategies.UNLUCKY_ATTEMPT_TO_CONSIDER_ODD_NUMBERS_ONLY);
        inputToStrategies.put("o2", PrimeNumberGenerator.Strategies.UNLUCKY_ATTEMPT_TO_CONSIDER_ODD_NUMBERS_ONLY);
    }


    public static String WRONG_INPUT_MESSAGE = "Usage: PrimeNumberGenerator STRATEGY_ID BEGIN_NUMBER END_NUMBER" + "\nuse --help for more information" + "\nuse test to check all algorithms";


    private static String wrongInputMessage() {
        return WRONG_INPUT_MESSAGE;
    }

    public static String HELP_MESSAGE = "\nUsage: PrimeNumberGenerator STRATEGY_ID BEGIN_NUMBER END_NUMBER" + "\nFor example PrimeNumberGenerator n 0 10" + "\n\nSTRATEGY_ID value is one of the following:" + inputToStrategies
        .entrySet().stream()
        .map(e -> "\t" + e.getKey() + "\t//" + e.getValue())
        .collect(Collectors.joining(
            "\n")) + "\nBEGIN_NUMBER is from 0 to INTEGER.MAXVALUE" + "\nEND_NUMBER is from 0 to INTEGER.MAXVALUE";

    private String printHelpMessage() {
        return HELP_MESSAGE;
    }

    private Map<ParsedInput.Status, Function<ParsedInput, PresentationModel>>
        inputFlagToInputHandler = new HashMap<>();


    {
        inputFlagToInputHandler.put(ParsedInput.Status.WRONG_FORMAT,
            inputResults -> {
                StringBuilder text = new StringBuilder(wrongInputMessage());
                text.append("\nwrong input format");
                if (inputResults.getCause() != null) {
                    text.append("\nCause ").append(inputResults.getCause().toString());
                }
                return new PresentationModel(ParsedInput.Status.WRONG_FORMAT, text.toString());
            }
        );
        inputFlagToInputHandler.put(ParsedInput.Status.HELP,
            inputResults -> new PresentationModel(ParsedInput.Status.HELP, printHelpMessage())
        );
        inputFlagToInputHandler.put(ParsedInput.Status.TEST,
            inputResults -> {
                generator.generateTest();
                return new PresentationModel(ParsedInput.Status.TEST, "");
            }
        );
        inputFlagToInputHandler.put(ParsedInput.Status.WRONG_STRATEGY,
            inputResults -> new PresentationModel(ParsedInput.Status.WRONG_STRATEGY,
                "\nwrong strategy" + wrongInputMessage())
        );

        inputFlagToInputHandler.put(ParsedInput.Status.OK,
            inputResults -> {
                List<Integer> primes = generator
                    .generate(inputResults.getStrategy(),
                        inputResults.getBegin(),
                        inputResults.getEnd());
                return new PresentationModel(ParsedInput.Status.OK,
                    primes.toString());
            }
        );
    }


    public static class PresentationModel {
        private final ParsedInput.Status status;
        private final String text;

        public PresentationModel(ParsedInput.Status status, String text) {
            this.status = status;
            this.text = text;

        }

        public String getText() {
            return text;
        }

        public ParsedInput.Status getStatus() {
            return status;
        }
    }

    /**
     * receives raw input,
     * 2) calls input parser
     * 3) invokes handler appropriate for parsed input status
     * 4) returns presentation model containing status and text to display     *
     * @param args raw input
     * @return presentation model
     */
    public PresentationModel execute(String[] args) {
        ParsedInput parameters = parametersParser.parseParameters(args);
        ParsedInput.Status status = parameters.getStatus();
        return inputFlagToInputHandler.get(status).apply(parameters);
    }
}
