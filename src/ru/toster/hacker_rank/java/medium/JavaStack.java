package ru.toster.hacker_rank.java.medium;

import java.util.*;

/**
 * @author Ivan Rovenskiy
 * 20 September 2020
 */
public class JavaStack {
    // Link: https://www.hackerrank.com/challenges/java-stack/problem

    public static void main(String[] argh) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String input = sc.next();

            System.out.println(isBalanced(input));
        }
    }

    private static boolean isBalanced(String str) {
        if (str.isEmpty()) {
            return true;
        }

        if (str.length() % 2 != 0) {
            return false;
        }

        final String[] symbolArray = str.split("");
        Deque<String> symbolStack = new ArrayDeque<>();

        try {
            for (String symbol : symbolArray) {
                if (dict.containsKey(symbol)) {
                    symbolStack.add(symbol);
                } else if (dict.containsValue(symbol)) {
                    if (dict.get(symbolStack.peekLast()).equals(symbol)) {
                        symbolStack.pollLast();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }

        return symbolStack.size() == 0;
    }

    private final static Map<String, String> dict = new HashMap<String, String>() {{
        put("{", "}");
        put("(", ")");
        put("[", "]");
    }};
}