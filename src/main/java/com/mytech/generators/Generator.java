package com.mytech.generators;

import java.util.List;

/**
 * 11, 13, 15, 17, 19, 21, 23, 25, 27, 29
 * only odd numbers, then add two
 * <p/>
 * 1  3        7  9
 * 0, 2, 4, 5, 6, 8
 * <p/>
 * 11 + 2
 * going only through odd numbers
 * <p/>
 * 1, 2, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25
 */

public interface Generator {
//        public String getName();

    public List<Integer> generate(int begin, int end);
}
