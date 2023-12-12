package test.java;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.ImmutableList;
import com.google.testing.junit.testparameterinjector.TestParameter;
import com.google.testing.junit.testparameterinjector.TestParameterInjector;
import java.time.format.DateTimeParseException;
import java.util.List;
import main.java.ProbableVacations;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Tests for {@link ProbableVacations}. */
@RunWith(TestParameterInjector.class)
public class ProbableVacationsTest {

  private static final ImmutableList<String> HOLIDAYS =
      ImmutableList.of(
          "01/26/2023",
          "03/08/2023",
          "03/22/2023",
          "03/30/2023",
          "04/07/2023",
          "05/01/2023",
          "06/29/2023",
          "08/15/2023",
          "09/19/2023",
          "10/02/2023",
          "10/24/2023",
          "11/13/2023",
          "12/25/2023");

  enum ProbableLeavesValidCase {
    EMPTY_INPUT_LIST(ImmutableList.of(), 1, ImmutableList.of()),
    VALID_INPUT_LIST_WITH_ZERO_DAY(HOLIDAYS, 0, ImmutableList.of()),
    VALID_INPUT_LIST_WITH_ONE_DAY(
        HOLIDAYS,
        1,
        ImmutableList.of(
            "01/27/2023",
            "03/31/2023",
            "04/06/2023",
            "04/10/2023",
            "04/28/2023",
            "05/02/2023",
            "06/30/2023",
            "08/14/2023",
            "09/18/2023",
            "09/29/2023",
            "10/03/2023",
            "10/23/2023",
            "11/10/2023",
            "11/14/2023",
            "12/22/2023",
            "12/26/2023")),
    VALID_INPUT_LIST_WITH_TWO_DAYS(
        HOLIDAYS,
        2,
        ImmutableList.of(
            "01/27/2023",
            "01/30/2023",
            "03/06/2023",
            "03/07/2023",
            "03/09/2023",
            "03/10/2023",
            "03/20/2023",
            "03/21/2023",
            "03/23/2023",
            "03/24/2023",
            "03/31/2023",
            "04/03/2023",
            "04/05/2023",
            "04/06/2023",
            "04/10/2023",
            "04/11/2023",
            "04/27/2023",
            "04/28/2023",
            "05/02/2023",
            "05/03/2023",
            "06/30/2023",
            "07/03/2023",
            "08/11/2023",
            "08/14/2023",
            "09/15/2023",
            "09/18/2023",
            "09/28/2023",
            "09/29/2023",
            "10/03/2023",
            "10/04/2023",
            "10/20/2023",
            "10/23/2023",
            "11/09/2023",
            "11/10/2023",
            "11/14/2023",
            "11/15/2023",
            "12/21/2023",
            "12/22/2023",
            "12/26/2023",
            "12/27/2023")),
    VALID_INPUT_LIST_WITH_THREE_DAYS(
        HOLIDAYS,
        3,
        ImmutableList.of(
            "01/24/2023",
            "01/25/2023",
            "01/27/2023",
            "01/30/2023",
            "01/31/2023",
            "03/03/2023",
            "03/06/2023",
            "03/07/2023",
            "03/09/2023",
            "03/10/2023",
            "03/13/2023",
            "03/17/2023",
            "03/20/2023",
            "03/21/2023",
            "03/23/2023",
            "03/24/2023",
            "03/27/2023",
            "03/28/2023",
            "03/29/2023",
            "03/31/2023",
            "04/03/2023",
            "04/04/2023",
            "04/05/2023",
            "04/06/2023",
            "04/10/2023",
            "04/11/2023",
            "04/12/2023",
            "04/26/2023",
            "04/27/2023",
            "04/28/2023",
            "05/02/2023",
            "05/03/2023",
            "05/04/2023",
            "06/27/2023",
            "06/28/2023",
            "06/30/2023",
            "07/03/2023",
            "07/04/2023",
            "08/10/2023",
            "08/11/2023",
            "08/14/2023",
            "08/16/2023",
            "08/17/2023",
            "09/14/2023",
            "09/15/2023",
            "09/18/2023",
            "09/20/2023",
            "09/21/2023",
            "09/27/2023",
            "09/28/2023",
            "09/29/2023",
            "10/03/2023",
            "10/04/2023",
            "10/05/2023",
            "10/19/2023",
            "10/20/2023",
            "10/23/2023",
            "10/25/2023",
            "10/26/2023",
            "11/08/2023",
            "11/09/2023",
            "11/10/2023",
            "11/14/2023",
            "11/15/2023",
            "11/16/2023",
            "12/20/2023",
            "12/21/2023",
            "12/22/2023",
            "12/26/2023",
            "12/27/2023",
            "12/28/2023"));

    final ImmutableList<String> holidays;
    final int noOfLeaveDays;
    final ImmutableList<String> possibleVacationDates;

    private ProbableLeavesValidCase(
        ImmutableList<String> holidays, int noOfDays, ImmutableList<String> possibleVacationDates) {
      this.holidays = holidays;
      this.noOfLeaveDays = noOfDays;
      this.possibleVacationDates = possibleVacationDates;
    }
  }

  @Test
  public void getProbableLeaveDates_returnsProbableLeaveDates(
      @TestParameter ProbableLeavesValidCase testCase) {
    // Act.
    List<String> result =
        ProbableVacations.getProbableLeaveDates(testCase.holidays, testCase.noOfLeaveDays);

    // Assert.
    assertThat(result).containsExactlyElementsIn(testCase.possibleVacationDates);
  }

  @Test
  public void getProbableLeaveDates_withInvalidDateFormat_throwsException() {
    // Act & Assert.
    assertThrows(
        DateTimeParseException.class,
        () ->
            ProbableVacations.getProbableLeaveDates(
                ImmutableList.of("26-01-2023", "08-03-2023", "13-11-2023", "25-12-2023"),
                /* noOfLeaveDays= */ 1));
  }

  @Test
  public void getProbableLeaveDates_withInvalidNumberOfLeaveDays_throwsException() {
    // Act & Assert.
    assertThrows(
        IllegalArgumentException.class,
        () ->
            ProbableVacations.getProbableLeaveDates(
                ImmutableList.of("26-01-2023", "08-03-2023", "13-11-2023", "25-12-2023"),
                /* noOfLeaveDays= */ 4));
  }
}
