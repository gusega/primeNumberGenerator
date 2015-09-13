package com.mytech.generators.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.mytech.generators.Generator;

public class OptimizedGenerator implements Generator {
    @Override
    public List<Integer> generate(int begin, int end) {
        List<Integer> primes = new ArrayList<>();
        int delimiterIndex = -1;

        int lastNumber = 1;
        boolean isPrime = true;
        while (lastNumber < end) {
            boolean isFactorOf5 = lastNumber % 5 == 0;
            /**
             * applying optimisations which would allow us
             * to find out if the number is not prime
             * without trying to factorize it with
             * all other primes
             */
            if (isFactorOf5 && lastNumber != 5) {
                lastNumber += 2;
                continue;
            }

            /**
             * No Optimisations worked out, we're still
             * not convinced that this number is not prime
             * so we're dividing it by all primes we know, one by one,
             * and if it doesn't divide by any of them without a reminder,
             * then it's prime
             */
            final int number = lastNumber;
            isPrime = !primes
                .parallelStream()
                .filter((prime) -> prime > 2) // no need to check if number divides by 1 or 2
                .anyMatch((prime) -> number % prime == 0);

            if (isPrime) {
                primes.add(lastNumber);
                if (lastNumber > begin && delimiterIndex == -1) {
                    delimiterIndex = primes.size() - 1;
                }
            }
            if (lastNumber == 1 || lastNumber == 2) {
                lastNumber += 1;
                /**
                 * because 2 and 3 are also primes.
                 */
            } else {
                lastNumber += 2;
                /**
                 * for other cases we could instantly incremend number by 2
                 * because incrementing it by 1 wont give us an odd number
                 */
            }
        }
        if (delimiterIndex == -1) {
            return Collections.emptyList();
        }
        return primes.subList(delimiterIndex, primes.size());
    }
}
