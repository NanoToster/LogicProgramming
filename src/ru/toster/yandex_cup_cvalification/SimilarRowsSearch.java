package ru.toster.yandex_cup_cvalification;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Ivan Rovenskiy
 * 25 October 2020
 */
public class SimilarRowsSearch {

    private static Set<SimilarPair> similarPairSet = new HashSet<>();
    private static List<Map<String, HashSet<Integer>>> keyToRowIndexMapList = new ArrayList<>();
    private static List<String[]> rowList = new ArrayList<>();

    public static void main(String[] args) {
        final List<Integer> significantColumnIndexList = new ArrayList<>();
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            final String[] firstLine = bufferedReader.readLine().split(" ");
            final int rowCount = Integer.parseInt(firstLine[0]);
            final int columnCount = Integer.parseInt(firstLine[1]);

            final String[] secondLineArray = bufferedReader.readLine().split(" ");
            for (int i = 1; i < secondLineArray.length; i++) {
                significantColumnIndexList.add(Integer.parseInt(secondLineArray[i]));
                keyToRowIndexMapList.add(new HashMap<>());
            }

            for (int i = 0; i < rowCount; i++) {
                final String[] rowArray = parseRow(bufferedReader.readLine(), columnCount);
                rowList.add(rowArray);
                for (int j = 0; j < significantColumnIndexList.size(); j++) {
                    final String valueInColumn = rowArray[significantColumnIndexList.get(j) - 1];
                    if (keyToRowIndexMapList.get(j).containsKey(valueInColumn)) {
                        keyToRowIndexMapList.get(j).get(valueInColumn).add(i);
                    } else {
                        final HashSet<Integer> rowSet = new HashSet<>();
                        rowSet.add(i);
                        keyToRowIndexMapList.get(j).put(valueInColumn, rowSet);
                    }
                }
            }

            for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
                for (int i = 0; i < significantColumnIndexList.size(); i++) {
                    final String significantValue = rowList.get(rowIndex)[significantColumnIndexList.get(i) - 1];
                    if (keyToRowIndexMapList.get(i).containsKey(significantValue)) {
                        final HashSet<Integer> rowIdsToCompareSet = keyToRowIndexMapList.get(i).get(significantValue);
                        for (Integer rowId : rowIdsToCompareSet) {
                            if (rowId != rowIndex) {
                                if (isSimilar(rowList.get(rowIndex), rowList.get(rowId))) {
                                    similarPairSet.add(new SimilarPair(rowIndex, rowId));
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(similarPairSet.size());

        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String[] parseRow(String row, int columnCount) {
        final String[] parsedRow = new String[columnCount];

        StringBuilder accumulator = new StringBuilder();
        int tabCount = 0;
        for (char ch : row.toCharArray()) {
            if (ch == '\t') {
                parsedRow[tabCount] = accumulator.toString();
                accumulator = new StringBuilder();
            }

            accumulator.append(ch);
        }
        if (parsedRow[parsedRow.length-1] == null) {
            parsedRow[parsedRow.length-1] = "";
        }
        return parsedRow;
    }

    private static boolean isSimilar(String[] left, String[] right) {
        for (int i = 0; i < left.length; i++) {
            if (left[i].isEmpty() || right[i].isEmpty()) {
                continue;
            }
            if (!left[i].equals(right[i])) {
                return false;
            }
        }
        return true;
    }

    private static class SimilarPair {
        private final int left;
        private final int right;

        public SimilarPair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimilarPair that = (SimilarPair) o;
            return (left == that.left && right == that.right)
                    || (left == that.right && right == that.left);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left + right);
        }
    }
}