package com.mytech.generators.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mytech.generators.Generator;

public class OptimizedGenerator2 implements Generator {
    @Override
    public List<Integer> generate(int begin, int end) {
        List<Integer> primes = new ArrayList<>();
        int delimiterIndex = -1;
        int[] initialPrimes = new int[]{1, 2, 3, 5};


        for (int lastNumber : initialPrimes) {
            if (lastNumber >= end) {
                break;
            }
            if (lastNumber > begin && delimiterIndex == -1) {
                delimiterIndex = primes.size();
            }
            primes.add(lastNumber);
        }


        int lastNumber = 7;
        while (lastNumber < end) {
            boolean isPrime = true;

            /**
             * applying optimisations which would allow us
             * to find out if the number is not prime
             * without trying to factorize it with
             * all other primes
             */
            if (lastNumber % 5 == 0) {
                // not prime, take next number, repeat loop
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
            for (int i = 2, primesSize = primes.size(); i < primesSize; i++) {
                // check starting from 3nd element. don't need to check
                // against 1 and 2
                int p = primes.get(i);
                if (!(isPrime = lastNumber % p != 0)) {
                    break;
                }
            }

            if (isPrime) {
                primes.add(lastNumber);
                if (lastNumber > begin && delimiterIndex == -1) {
                    delimiterIndex = primes.size() - 1;
                }
            }
            lastNumber += 2;
        }

        if (delimiterIndex == -1) {
            return Collections.emptyList();
        }
        return primes.subList(delimiterIndex, primes.size());
    }
}
