# Vacation-Predictor

**Problem Description: Vacation Predictor** \
Your organization wants to know the probable dates when a person can take leave based on the holidays and weekends in a calendar year.

Write a function that accepts two parameters:
1. A list of string input holiday dates for a year.
2.The number of days a person can take leave in a leave application.

**Returns**
- A string list containing the probable vacation dates based on the number of days a person can take leave.

**Function Signature** \
ImmutableList<String> getProbableLeaveDates(List<String> holidayDatesInYear, int noOfLeaveDays);

**Sample Input** \
holidayDatesInYear: [ "01/26/2023", "03/08/2023", "06/29/2023", "08/15/2023", "11/13/2023", "12/25/2023"] \
noOfDays: 1

**Sample Output** \
[01/27/2023, 06/30/2023, 08/14/2023, 11/10/2023, 11/14/2023, 12/22/2023, 12/26/2023]

**Constraints**
- The input string holiday dates should be in the format "MM/dd/yyyy".
- The no. of leave days can be 1, 2, or 3.
