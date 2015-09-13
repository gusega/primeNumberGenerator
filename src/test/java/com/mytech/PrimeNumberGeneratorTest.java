package com.mytech;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

import com.mytech.generatorservice.PrimeNumberGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PrimeNumberGeneratorTest {

    @Parameterized.Parameters(name = "{index}: strategy={0}")
    public static Collection<Object[]> data() {
        PrimeNumberGenerator.Strategies[] values = PrimeNumberGenerator.Strategies.values();
        return Arrays.stream(values)
            .map(v -> new Object[]{v})
            .collect(Collectors.toList());
    }

    private PrimeNumberGenerator.Strategies strategy;

    public PrimeNumberGeneratorTest(PrimeNumberGenerator.Strategies s) {
        strategy = s;
    }
    private PrimeNumberGenerator generator = new PrimeNumberGenerator();

    @Test
    public void testGeneratePrimeNumbersInitial() throws Exception {
        List<Integer> expected =
            Arrays.asList(2, 3, 5, 7);
        assertEquals(expected,
            generator.generate(strategy, 1, 10).getPrimes());
    }

    @Test
    public void testGeneratePrimeNumbersInitialBigger() throws Exception {
        List<Integer> expected =
            Arrays.asList(2, 3, 5, 7, 11);
        assertEquals(expected,
            generator.generate(strategy, 1, 12).getPrimes());
    }
    @Test
    public void testGeneratePrimeNumbersInitialSmaller() throws Exception {
        List<Integer> expected =
            Arrays.asList(2, 3, 5);
        assertEquals(expected,
            generator.generate(strategy, 1, 7).getPrimes());
    }

    @Test
    public void testGeneratePrimeSingle() throws Exception {
        assertEquals(
            Collections.singletonList(1),
            generator.generate(strategy, 0, 2).getPrimes());
    }

    @Test
    public void testGeneratePrimeEmptyOne() throws Exception {
        List<Integer> expected = Collections.emptyList();
        assertEquals(expected,
            generator.generate(strategy, 1, 1).getPrimes());
    }

    @Test
    public void testGeneratePrimeEmptyAboveOne() throws Exception {
        List<Integer> expected = Collections.emptyList();
        assertEquals(expected,
            generator.generate(strategy, 2, 2).getPrimes());
    }

    @Test
    public void testGeneratePrimeSomeSingleNumber() throws Exception {
        List<Integer> expected = Arrays.asList(11, 13);
        assertEquals(expected,
            generator.generate(strategy, 9, 14).getPrimes());
    }

    @Test
    public void testPrintPrimes() throws Exception {
        System.out.println(
            generator.generate(strategy, 0, 9999));
    }

    @Test
    public void testWrongInput() throws Exception {
        List<Integer> expected = Collections.emptyList();
        assertEquals(expected,
            generator.generate(strategy, 4, 2).getPrimes());
    }

}
