package ru.toster.yandex_cup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivan Rovenskiy
 * 22 September 2020
 */
public class Alarms {
    public static void main(String[] args) {
        final long numberOfAlarmClocks;
        final long stepOfCalls;
        final int numberOfAlarmsToWakeUp;
        final List<Long> alarmTimeList;

        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            final String[] firstLineArray = bufferedReader.readLine().split(" ");
            numberOfAlarmClocks = Long.parseLong(firstLineArray[0]);
            stepOfCalls = Long.parseLong(firstLineArray[1]);
            numberOfAlarmsToWakeUp = Integer.parseInt(firstLineArray[2]);

            alarmTimeList = Arrays.stream(bufferedReader.readLine().split(" "))
                    .map(Long::parseLong)
                    .collect(Collectors.toCollection(HashSet::new))
                    .stream()
                    .sorted()
                    .collect(Collectors.toCollection(LinkedList::new));
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

        if (numberOfAlarmsToWakeUp < 1) {
            System.out.println(0);
            return;
        }

        Set<Long> containsTrololo = new HashSet<>(alarmTimeList);
        for (int i = 0; i < numberOfAlarmsToWakeUp; i++) {
            addNextAlarmToList(alarmTimeList, alarmTimeList.get(i) + stepOfCalls, containsTrololo);
        }
        System.out.println(alarmTimeList.get(numberOfAlarmsToWakeUp - 1));
    }

    private static void addNextAlarmToList(List<Long> alarmTimeList, long newAlarmTime, Set<Long> containsTrololo) {
        if (containsTrololo.contains(newAlarmTime)) {
            return;
        }
        containsTrololo.add(newAlarmTime);

        if (newAlarmTime > alarmTimeList.get(alarmTimeList.size() - 1)) {
            alarmTimeList.add(newAlarmTime);
            return;
        }

        int crutch = 1;
        while (true) {
            if (containsTrololo.contains(newAlarmTime + crutch)) {
                alarmTimeList.add(alarmTimeList.indexOf(newAlarmTime + crutch), newAlarmTime);
                break;
            }
            if (containsTrololo.contains(newAlarmTime - crutch)) {
                alarmTimeList.add(alarmTimeList.indexOf(newAlarmTime - crutch) + 1, newAlarmTime);
                break;
            }
            crutch++;
        }
    }
}