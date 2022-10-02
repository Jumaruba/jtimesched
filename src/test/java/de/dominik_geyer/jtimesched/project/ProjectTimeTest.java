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
      assertEquals(ProjectTime.parseSeconds("0:00:00"), 0);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseSecondsTest() {
    try {
      assertEquals(ProjectTime.parseSeconds("0:00:05"), 5);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseMinutesTest() {
    try {
      assertEquals(ProjectTime.parseSeconds("0:15:48"), 948);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseHoursTest() {
    try {
      assertEquals(ProjectTime.parseSeconds("6:15:48"), 22548);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @Test
  public void parseDaysTest() {
    try {
      assertEquals(ProjectTime.parseSeconds("36:15:48"), 130548);
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
