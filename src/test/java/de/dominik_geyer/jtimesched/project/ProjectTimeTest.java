package de.dominik_geyer.jtimesched.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ProjectTimeTest {
  @Test
  public void parseZeroTimeTest() {
    try {
      // Given 
      String strTime = "0:00:00";

      // When
      int result = ProjectTime.parseSeconds(strTime);

      // Then
      assertEquals(0, result);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseSecondsTest() {
    try {
      // Given 
      String strTime = "0:00:05";

      // When
      int result = ProjectTime.parseSeconds(strTime);

      // Then
      assertEquals(5, result);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseMinutesTest() {
    try {
      // Given 
      String strTime = "0:15:48";

      // When
      int result = ProjectTime.parseSeconds(strTime);

      // Then
      assertEquals(948, result);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseHoursTest() {
    try {
      // Given 
      String strTime = "6:15:48";

      // When
      int result = ProjectTime.parseSeconds(strTime);

      // Then
      assertEquals(22548, result);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseDaysTest() {
    try {
      // Given 
      String strTime = "36:15:48";

      // When
      int result = ProjectTime.parseSeconds(strTime);

      // Then
      assertEquals(130548, result);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @ParameterizedTest
  @MethodSource("parseSecondsInvalidParams")
  public void parseSecondsInvalidTest(String argument) {
    assertThrows(ParseException.class, () -> ProjectTime.parseSeconds(argument));
  }

  static Stream<String> parseSecondsInvalidParams() {
    return Stream.of("", "2", "0:07:80", null);
  }
}
