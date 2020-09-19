package ru.toster.hacker_rank.java.easy;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Ivan Rovenskiy
 * 20 September 2020
 */
public class JavaPrimalityTest {
    // Link: https://www.hackerrank.com/challenges/java-primality-test/problem

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String number = scanner.nextLine();

        final BigInteger bigNumber = new BigInteger(number);

        if (bigNumber.isProbablePrime(100)) {
            System.out.println("prime");
        } else {
            System.out.println("not prime");
        }

        scanner.close();
    }
}