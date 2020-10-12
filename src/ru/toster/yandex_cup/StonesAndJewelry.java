package ru.toster.yandex_cup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Ivan Rovenskiy
 * 22 September 2020
 */
public class StonesAndJewelry {
    /*
    Даны две строки строчных латинских символов: строка J и строка S.
    Символы, входящие в строку J, — «драгоценности», входящие в строку S — «камни».
    Нужно определить, какое количество символов из S одновременно являются «драгоценностями».
    Проще говоря, нужно проверить, какое количество символов из S входит в J.
    */
    public static void main(String[] args) {
        final String jewelry;
        final String stones;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            jewelry = bufferedReader.readLine();
            stones = bufferedReader.readLine();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

        if (jewelry.isEmpty() || stones.isEmpty()) {
            System.out.println(0);
            return;
        }

        final long result = Arrays.stream(stones.split(""))
                .filter(jewelry::contains)
                .count();
        System.out.println(result);
    }
}