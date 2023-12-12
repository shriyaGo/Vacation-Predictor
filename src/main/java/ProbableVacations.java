package main.java;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ProbableVacations {

  /**
   * Problem Description: Vacation Predictor Your organization wants to know the probable dates when
   * a person can take leave based on the holidays and weekends in a calendar year.
   *
   * <p>Write a function that accepts two parameters: 1. A list of string input holiday dates for a
   * year. 2.The number of days a person can take leave in a leave application.
   */
  /**
   * Returns a string list containing the probable vacation dates based on the number of days a
   * person can take leave.
   *
   * @param holidayDatesInYear List of holidays in a year.
   * @param noOfLeaveDays The number of days a person can take leave.
   * @return String list containing the probable vacation dates.
   * @throws IllegalArgumentException if {@code noOfLeaveDays} is greater than 3 or less than
   *     0.
   * @throws DateTimeParseException if {@code holidayDatesInYear} has dates not in the format
   *     "MM/dd/yyyy".
   */
  public static List<String> getProbableLeaveDates(
      List<String> holidayDatesInYear, int noOfLeaveDays) {
    if (noOfLeaveDays > 3 || noOfLeaveDays < 0) {
      throw new IllegalArgumentException("Number of leave days should be 1, 2, or 3.");
    }

    Set<LocalDate> result = new HashSet<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    List<LocalDate> holidayDates =
        holidayDatesInYear.stream()
            .map(holiday -> LocalDate.parse(holiday, formatter))
            .collect(Collectors.toUnmodifiableList());
    Map<LocalDate, DayOfWeek> resultMap =
        holidayDates.stream()
            .collect(Collectors.toMap(Function.identity(), LocalDate::getDayOfWeek));

    resultMap
        .entrySet()
        .removeIf(
            entry ->
                !(entry.getValue().plus(noOfLeaveDays + 1).equals(SATURDAY)
                    || entry.getValue().minus(noOfLeaveDays + 1).equals(SUNDAY)
                    || (((entry.getValue().getValue() + 7 - noOfLeaveDays) >= SATURDAY.getValue())
                    && ((entry.getValue().getValue() + 7 - noOfLeaveDays) <= SUNDAY.getValue()))
                    || (((entry.getValue().getValue() + noOfLeaveDays) >= SATURDAY.getValue())
                    && ((entry.getValue().getValue() + noOfLeaveDays) <= SUNDAY.getValue()))
                    || entry.getValue().plus(1).equals(SATURDAY)
                    || entry.getValue().minus(1).equals(SUNDAY)));

    for (Entry<LocalDate, DayOfWeek> entry : resultMap.entrySet()) {

      if (entry.getValue().plus(noOfLeaveDays + 1).equals(SATURDAY)) {
        if (entry.getValue().getValue() < WEDNESDAY.getValue()) {
          LocalDate date = entry.getKey().plusDays(noOfLeaveDays - 1);
          DayOfWeek dayOfWeek = entry.getValue().plus(noOfLeaveDays - 1);
          while (!dayOfWeek.equals(entry.getValue())) {
            result.add(date);
            date = date.minusDays(1);
            dayOfWeek = dayOfWeek.minus(1);
          }
        } else {
          LocalDate date = entry.getKey().plusDays(1);
          DayOfWeek dayOfWeek = entry.getValue().plus(1);
          while (!dayOfWeek.equals(SATURDAY)) {
            result.add(date);
            date = date.plusDays(1);
            dayOfWeek = dayOfWeek.plus(1);
          }
        }
      }

      if (entry.getValue().minus(noOfLeaveDays + 1).equals(SUNDAY)) {
        if (entry.getValue().getValue() > WEDNESDAY.getValue()) {
          LocalDate date = entry.getKey().minusDays(noOfLeaveDays - 1);
          DayOfWeek dayOfWeek = entry.getValue().minus(noOfLeaveDays - 1);
          while (!dayOfWeek.equals(entry.getValue())) {
            result.add(date);
            date = date.plusDays(1);
            dayOfWeek = dayOfWeek.plus(1);
          }
        } else {
          LocalDate date = entry.getKey().minusDays(1);
          DayOfWeek dayOfWeek = entry.getValue().minus(1);
          while (!dayOfWeek.equals(SUNDAY)) {
            result.add(date);
            date = date.minusDays(1);
            dayOfWeek = dayOfWeek.minus(1);
          }
        }
      }

      if (entry.getValue().plus(1).equals(SATURDAY)) {
        {
          LocalDate date = entry.getKey().minusDays(noOfLeaveDays);
          DayOfWeek dayOfWeek = entry.getValue().minus(noOfLeaveDays);
          while (!dayOfWeek.equals(entry.getValue())) {
            result.add(date);
            date = date.plusDays(1);
            dayOfWeek = dayOfWeek.plus(1);
          }
        }
        {
          LocalDate date = entry.getKey().plusDays(noOfLeaveDays + 2);
          DayOfWeek dayOfWeek = entry.getValue().plus(noOfLeaveDays + 2);
          while (!dayOfWeek.equals(SUNDAY)) {
            result.add(date);
            date = date.minusDays(1);
            dayOfWeek = dayOfWeek.minus(1);
          }
        }
      }

      if (entry.getValue().minus(1).equals(SUNDAY)) {
        {
          LocalDate date = entry.getKey().plusDays(noOfLeaveDays);
          DayOfWeek dayOfWeek = entry.getValue().plus(noOfLeaveDays);
          while (!dayOfWeek.equals(entry.getValue())) {
            result.add(date);
            date = date.minusDays(1);
            dayOfWeek = dayOfWeek.minus(1);
          }
        }
        {
          LocalDate date = entry.getKey().minusDays(noOfLeaveDays + 2);
          DayOfWeek dayOfWeek = entry.getValue().minus(noOfLeaveDays + 2);
          while (!dayOfWeek.equals(SATURDAY)) {
            result.add(date);
            date = date.plusDays(1);
            dayOfWeek = dayOfWeek.plus(1);
          }
        }
      }

      if (((entry.getValue().getValue() + noOfLeaveDays) >= SATURDAY.getValue())
          && ((entry.getValue().getValue() + noOfLeaveDays) <= SUNDAY.getValue())) {
        {
          LocalDate date = entry.getKey().plusDays(noOfLeaveDays + 2);
          DayOfWeek dayOfWeek = entry.getValue().plus(noOfLeaveDays + 2);
          while (!dayOfWeek.equals(SUNDAY)) {
            result.add(date);
            date = date.minusDays(1);
            dayOfWeek = dayOfWeek.minus(1);
          }
        }
        {
          LocalDate date = entry.getKey().plusDays(1);
          DayOfWeek dayOfWeek = entry.getValue().plus(1);
          while (!dayOfWeek.equals(SATURDAY)) {
            result.add(date);
            date = date.plusDays(1);
            dayOfWeek = dayOfWeek.plus(1);
          }
        }
      }

      if (((entry.getValue().getValue() + 7 - noOfLeaveDays) >= SATURDAY.getValue())
          && ((entry.getValue().getValue() + 7 - noOfLeaveDays) <= SUNDAY.getValue())) {
        {
          LocalDate date = entry.getKey().minusDays(noOfLeaveDays + 2);
          DayOfWeek dayOfWeek = entry.getValue().minus(noOfLeaveDays + 2);
          while (!dayOfWeek.equals(SATURDAY)) {
            result.add(date);
            date = date.plusDays(1);
            dayOfWeek = dayOfWeek.plus(1);
          }
        }
        {
          LocalDate date = entry.getKey().minusDays(1);
          DayOfWeek dayOfWeek = entry.getValue().minus(1);
          while (!dayOfWeek.equals(SUNDAY)) {
            result.add(date);
            date = date.minusDays(1);
            dayOfWeek = dayOfWeek.minus(1);
          }
        }
      }
    }

    return result.stream()
        .sorted()
        .map(holidayDate -> holidayDate.format(formatter))
        .collect(Collectors.toUnmodifiableList());
  }

  private ProbableVacations() {
  }
}
