package ru.toster.yandex_cup_cvalification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Rovenskiy
 * 25 October 2020
 */
public class DescArraySort {
    public static void main(String[] args) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            final String host = bufferedReader.readLine();
            final String port = bufferedReader.readLine();
            final String a = bufferedReader.readLine();
            final String b = bufferedReader.readLine();
            final List<Integer> numberList = getNumberListFromRemote(host, port, a, b);

            numberList.sort(Integer::compareTo);
            for (int i = numberList.size() - 1; i >= 0; i--) {
                System.out.println(numberList.get(i));
            }
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<Integer> getNumberListFromRemote(final String host, final String port, final String a, final String b) throws Exception {
        final String url = host + ":" + port + "?a=" + a + "&b=" + b;
        final URLConnection connection = new URL(url).openConnection();

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        final String jsonArray = bufferedReader.lines()
                .collect(Collectors.joining());
        return Arrays.stream(jsonArray.substring(
                jsonArray.indexOf("[") + 1,
                jsonArray.indexOf("]"))
                .split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}