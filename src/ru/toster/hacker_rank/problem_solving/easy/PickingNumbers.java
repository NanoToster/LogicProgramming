package ru.toster.hacker_rank.problem_solving.easy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Rovenskiy
 * 19 September 2020
 */
public class PickingNumbers {
    // Link: https://www.hackerrank.com/challenges/picking-numbers/problem
    private static final Map<Integer, Integer> numberToCountMap = new HashMap<>();

    private static int pickingNumbers(List<Integer> inputList) {
        for (Integer number : inputList) {
            if (numberToCountMap.containsKey(number)) {
                numberToCountMap.put(number, numberToCountMap.get(number) + 1);
            } else {
                numberToCountMap.put(number, 1);
            }
        }

        if (numberToCountMap.size() == 1) {
            return numberToCountMap.values().stream().findFirst().orElse(0);
        }

        int maxSubArrayLength = 0;
        for (Integer number : inputList) {
            final int leftBorder = getCountForNumberOrMinValue(number - 1);
            final int rightBorder = getCountForNumberOrMinValue(number + 1);

            final int maxSubArrayLengthForCurrentNumber = numberToCountMap.get(number) + Math.max(Math.max(leftBorder, rightBorder), 0);
            if (maxSubArrayLengthForCurrentNumber > maxSubArrayLength) {
                maxSubArrayLength = maxSubArrayLengthForCurrentNumber;
            }
        }

        return maxSubArrayLength;
    }

    private static int getCountForNumberOrMinValue(int number) {
        return numberToCountMap.get(number) == null
                ? Integer.MIN_VALUE
                : numberToCountMap.get(number);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(pickingNumbers(new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(2);
            add(2);
            add(2);
        }}));
    }
}