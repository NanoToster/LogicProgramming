package ru.toster.yandex_cup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ivan Rovenskiy
 * 16 October 2020
 */
public class InterestingGame {
    private static int maxPoints;
    private static int numberOfCards;
    private static String[] cardNumberArray;

    public static void main(String[] args) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            final String[] firstLine = bufferedReader.readLine().split(" ");
            cardNumberArray = bufferedReader.readLine().split(" ");
            maxPoints = Integer.parseInt(firstLine[0]);
            numberOfCards = Integer.parseInt(firstLine[1]);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

        processCardsArray();
    }

    private static void processCardsArray() {
        int petya = 0; // 3
        int vasya = 0; // 5

        for (String cardNumber : cardNumberArray) {
            if (petya >= maxPoints || vasya >= maxPoints) {
                break;
            }

            final boolean multiplesFive = isMultiplesFive(cardNumber);
            final boolean multiplesThree = isMultiplesThree(cardNumber);

            if (multiplesFive && multiplesThree) {
                continue;
            }
            if (!multiplesFive && !multiplesThree) {
                continue;
            }

            if (multiplesThree) {
                petya++;
            }

            if (multiplesFive) {
                vasya++;
            }
        }

        printResults(petya, vasya);
    }

    private static void printResults(int petya, int vasya) {
        if (petya > vasya) {
            System.out.println("Petya");
        } else if (vasya > petya) {
            System.out.println("Vasya");
        } else {
            System.out.println("Draw");
        }
    }

    private static boolean isMultiplesThree(String number) {
        final char[] chars = number.toCharArray();
        if (chars.length == 1 && chars[0] == '0') {
            return false;
        }

        int sum = 0;
        for (char symbol : chars) {
            sum = sum + (int) symbol;
        }

        return sum % 3 == 0;
    }

    private static boolean isMultiplesFive(String number) {
        final char[] chars = number.toCharArray();
        if (chars.length == 1 && chars[0] == '0') {
            return false;
        }
        final char lastSymbol = chars[chars.length - 1];
        return lastSymbol == '5' || lastSymbol == '0';
    }
}