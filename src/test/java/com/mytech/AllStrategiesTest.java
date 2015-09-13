package com.mytech;

import java.util.List;

import com.mytech.generatorservice.PrimeNumberGenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AllStrategiesTest {

    private PrimeNumberGenerator generator = new PrimeNumberGenerator();

    @Test
    public void testCompareAllStrategies() throws Exception {
        List<Integer> r = null;
        for (PrimeNumberGenerator.Strategies s : PrimeNumberGenerator.Strategies.values()) {
            if (r == null) {
                System.out.println(
                    String.format("Initial strategy: %s", s)
                );
                r = generator.generate(s, 0, 9999);
            } else {
                assertEquals(r, generator.generate(s, 0, 9999));
                System.out.println(String.format("Strategy: %s asserted", s));
            }
        }
    }

    @Test
    public void testCompareAllStrategiesFromNumber() throws Exception {
        List<Integer> r = null;
        for (PrimeNumberGenerator.Strategies s : PrimeNumberGenerator.Strategies.values()) {
            List<Integer> generate = generator.generate(s, 999, 9999);
            if (r == null) {
                System.out.println(
                    String.format("Initial strategy: %s", s)
                );
                r = generate;
            } else {
                assertEquals(r, generate);
                System.out.println(String.format("Strategy: %s asserted", s));
            }
        }
    }

    @Test
    public void testCompareTwoStrategies() throws Exception {
        List<Integer> g2 = generator.generate(PrimeNumberGenerator.Strategies.UNLUCKY_ATTEMPT_TO_CONSIDER_ODD_NUMBERS_ONLY, 0, 9999);
        List<Integer> g1 = generator.generate(PrimeNumberGenerator.Strategies.NO_OPTIMISATION, 0, 9999);
        System.out.println(g1);
        System.out.println(g1.size());
        System.out.println(g2.size());
        g1.removeAll(g2);
        System.out.println(g1);
        assertTrue(g1.size() == 0);
    }
}
