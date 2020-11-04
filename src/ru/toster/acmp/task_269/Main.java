package ru.toster.acmp.task_269;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Ivan Rovenskiy
 * 28 October 2020
 */
public class Main {
    public static void main(String[] args) {
        final List<Integer> shortList;
        final List<Integer> longList;

        final Scanner in = new Scanner(System.in);
        final List<Integer> firstList = Arrays.stream(in.nextLine().trim().split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        final List<Integer> secondList = Arrays.stream(in.nextLine().trim().split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        if (firstList.size() >= secondList.size()) {
            longList = firstList;
            shortList = secondList;
        } else {
            longList = secondList;
            shortList = firstList;
        }

        extendListSizeAndPreparePositions(longList, shortList);

        int minLength = findCompatibilityWithMinLength(longList, shortList, longList.size() + shortList.size());

        System.out.println(minLength);
        System.out.flush();
    }

    private static int findCompatibilityWithMinLength(List<Integer> longList, List<Integer> shortList, int minLength) {
        int length = checkCompatibilityAndCalculateLength(longList, shortList, minLength);

        if (shortList.get(shortList.size() - 1) != 0) {
            return Math.min(length, minLength);
        }

        moveArrayToLeftSide(shortList);

        return findCompatibilityWithMinLength(longList, shortList, Math.min(length, minLength));
    }

    private static void moveArrayToLeftSide(List<Integer> shortList) {
        for (int i = shortList.size() - 1; i >= 0; i--) {
            if (shortList.get(i) != 0) {
                Collections.swap(shortList, i + 1, i);
            }
        }
    }

    private static int checkCompatibilityAndCalculateLength(List<Integer> longList, List<Integer> shortList, int minLength) {
        int length = 0;
        for (int i = 0; i < longList.size(); i++) {
            if (longList.get(i) + shortList.get(i) > 3) {
                return minLength;
            }
            if (longList.get(i) != 0 || shortList.get(i) != 0) {
                length++;
            }
        }
        return length;
    }

    private static void extendListSizeAndPreparePositions(List<Integer> longList, List<Integer> shortList) {
        final int shortSize = shortList.size();
        final int longSize = longList.size();
        for (int i = 0; i < shortSize; i++) {
            longList.add(0, 0);
            longList.add(0);
            shortList.add(0);
        }

        for (int i = 0; i < longSize; i++) {
            shortList.add(0);
        }
    }
}