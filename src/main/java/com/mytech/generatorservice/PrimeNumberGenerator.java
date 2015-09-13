package com.mytech.generatorservice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.mytech.generators.Generator;
import com.mytech.generators.impl.OptimizedGenerator;
import com.mytech.generators.impl.OptimizedGenerator2;
import com.mytech.generators.impl.SimpleGenerator;

public class PrimeNumberGenerator {
    private static Logger logger = Logger.getLogger(PrimeNumberGenerator.class.getSimpleName());


    private Generator simpleGeneratorWithoutOptimisations =
        new SimpleGenerator((i) -> false);
    private Generator simpleGeneratorWithOptimisation52 =
        new SimpleGenerator((i) -> (i > 2 && i % 2 == 0) || (i > 5 && i % 5 == 0));
    private Generator optimizedGenerator = new OptimizedGenerator();
    private Generator optimizedGenerator2 = new OptimizedGenerator2();


    private Map<Strategies, Generator> strategyToGenerator = new HashMap<>();

    {
        strategyToGenerator.put(Strategies.NO_OPTIMISATION,
            simpleGeneratorWithoutOptimisations);
        strategyToGenerator.put(Strategies.FILTER_OUT_EVEN_AND_5MULTIPLES,
            simpleGeneratorWithOptimisation52);
        strategyToGenerator.put(Strategies.UNLUCKY_ATTEMPT_TO_CONSIDER_ODD_NUMBERS_ONLY,
            optimizedGenerator);
        strategyToGenerator.put(Strategies.ITERATE_BY_ODD_NUMBERS_ONLY,
            optimizedGenerator2);
    }

    public static class GenerationResult {
        private final List<Integer> primes;
        private final long timeToGenerate;

        public GenerationResult(List<Integer> primes, long timeToGenerate) {
            this.primes = primes;
            this.timeToGenerate = timeToGenerate;
        }

        public List<Integer> getPrimes() {
            return primes;
        }

        public long getTimeToGenerate() {
            return timeToGenerate;
        }

        @Override
        public String toString() {
            return "GenerationResult{" +
                "primes=" + primes +
                ", timeToGenerate=" + timeToGenerate +
                '}';
        }

        public static GenerationResult EMPTY = new GenerationResult(Collections.emptyList(), 0);

    }


    public GenerationResult generate(Strategies strategy, int begin, int end) {
        long timestamp = System.nanoTime();
        if (begin >= end || begin < 0 || end < 0) {
            /**
             * if begin > end, begin < 0, end < 0
             * return empty collection
             */
            return GenerationResult.EMPTY;
        }
        List<Integer> primes = strategyToGenerator.get(strategy).generate(begin, end);
        long time = Math.round((System.nanoTime() - timestamp) * Math.pow(10, -6));
        logger.info(String.format("Items generated: %s, duration: %s milliseconds. Strategy %s",
            primes.size(),
            time,
            strategy.name()));
        return new GenerationResult(primes, time);
    }

    public enum Strategies {
        NO_OPTIMISATION,
        FILTER_OUT_EVEN_AND_5MULTIPLES,
        UNLUCKY_ATTEMPT_TO_CONSIDER_ODD_NUMBERS_ONLY,
        ITERATE_BY_ODD_NUMBERS_ONLY;
    }
}
