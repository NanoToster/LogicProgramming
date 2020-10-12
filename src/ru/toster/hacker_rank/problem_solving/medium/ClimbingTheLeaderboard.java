package ru.toster.hacker_rank.problem_solving.medium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Rovenskiy
 * 19 September 2020
 */
public class ClimbingTheLeaderboard {
    // Link: https://www.hackerrank.com/challenges/climbing-the-leaderboard/problem?h_r=profile
    static class Result {
        static class PlaceToRange {
            private final int place;
            private final int high;
            private final int low;

            public PlaceToRange(final int place, final int high, final int low) {
                this.place = place;
                this.high = high;
                this.low = low;
            }

            public boolean contains(final int value) {
                return value <= high && value >= low;
            }

            public int getPlace() {
                return place;
            }
        }

        public static List<Integer> climbingLeaderboard(final List<Integer> rankList, final List<Integer> playerRateList) {
            final List<PlaceToRange> placeToRangeList = buildPlaceToRangeList(rankList);

            final List<Integer> resultPlaceList = new ArrayList<>();
            int rangePointer = placeToRangeList.size() - 1;
            for (int i = 0; i < playerRateList.size(); i++) {
                if (rangePointer < 0) {
                    resultPlaceList.add(1);
                    continue;
                }
                while (true) {
                    if (placeToRangeList.get(rangePointer).contains(playerRateList.get(i))) {
                        resultPlaceList.add(placeToRangeList.get(rangePointer).getPlace());
                        break;
                    } else {
                        rangePointer--;
                        if (rangePointer < 0) {
                            break;
                        }
                    }
                }
            }
            return resultPlaceList;
        }

        public static List<PlaceToRange> buildPlaceToRangeList(final List<Integer> rankList) {
            final List<PlaceToRange> placeToRangeList = new ArrayList<>();

            int currentPosition = 1;
            int prevRank = -1;
            for (int i = 0; i < rankList.size(); i++) {
                if (i == 0) {
                    placeToRangeList.add(new PlaceToRange(currentPosition, Integer.MAX_VALUE, rankList.get(i)));
                    prevRank = rankList.get(i);
                    currentPosition++;
                } else {
                    if (prevRank != rankList.get(i)) {
                        placeToRangeList.add(new PlaceToRange(
                                currentPosition, rankList.get(i - 1) - 1, rankList.get(i)));
                        prevRank = rankList.get(i);
                        currentPosition++;
                    }
                }
            }
            placeToRangeList.add(new PlaceToRange(currentPosition, rankList.get(rankList.size() - 1) - 1, 0));

            return placeToRangeList;
        }
    }

    public static void main(String[] args) throws IOException {
        List<Integer> result = Result.climbingLeaderboard(
                new ArrayList<Integer>() {{
                    add(1);
                }},
                new ArrayList<Integer>() {{
                    add(1);
                    add(1);
                }});

        result.forEach(System.out::println);
    }
}