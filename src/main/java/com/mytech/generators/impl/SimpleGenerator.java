package com.mytech.generators.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.mytech.generators.Generator;

public class SimpleGenerator implements Generator {
    private final Predicate<Integer> optimisation;

    public SimpleGenerator(Predicate<Integer> p) {
        optimisation = p;
    }

    public String getName() {
        return "Simple generator";
    }

    @Override
    public List<Integer> generate(int begin, int end) {
        ArrayList<Integer> primes = new ArrayList<>();
        int delimiterIndex = -1;
        Integer lastNumber;
        if (primes.isEmpty()) {
            lastNumber = 1;
        } else {
            lastNumber = primes.get(primes.size() - 1) + 1;
        }
        boolean isPrime;
        while (lastNumber < end) {
            isPrime = !optimisation.test(lastNumber);
            if (lastNumber != 1 && lastNumber != 2 && isPrime) {
                for (int i = 1; i < primes.size(); i++) {
                    /**
                     * don't need to check first prime - 1
                     * as any number is always devisible to 1
                     * without a remainder
                     */

                    int prime = primes.get(i);
                    isPrime = lastNumber % prime != 0;
                    if (!isPrime) {
                        break;
                    }
                }
            }
            if (isPrime) {
                primes.add(lastNumber);
                if (lastNumber > begin && delimiterIndex == -1) {
                    delimiterIndex = primes.size() - 1;
                }
            }
            lastNumber += 1;
        }

        if (delimiterIndex == -1) {
            return Collections.emptyList();
        }
        return primes.subList(delimiterIndex, primes.size());
    }
}
