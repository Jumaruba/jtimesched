package de.dominik_geyer.jtimesched.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProjectTimeTest {
  @ParameterizedTest
  @MethodSource("parseSecondsValidParams")
  public void parseZeroTimeTest(String strTime, int expected) {
    // Given `strTime`

    try {
      // When
      int result = ProjectTime.parseSeconds(strTime);

      // Then
      assertEquals(expected, result);
    } catch (ParseException e) {
      fail("Should not have thrown any exception");
    }
  }

  @ParameterizedTest
  @MethodSource("parseSecondsInvalidParams")
  public void parseSecondsInvalidTest(String argument) {
    assertThrows(
        ParseException.class, () -> ProjectTime.parseSeconds(argument));
  }

  static Stream<Arguments> parseSecondsValidParams() {
    return Stream.of(
        Arguments.of("0:00:00", 0),
        Arguments.of("0:00:05", 5),
        Arguments.of("0:00:5", 5),
        Arguments.of("0:15:48", 948),
        Arguments.of("0:15:8", 908),
        Arguments.of("0:5:8", 308),
        Arguments.of("6:15:48", 22548),
        Arguments.of("36:15:48", 130548));
  }

  static Stream<String> parseSecondsInvalidParams() {
    return Stream.of("", "0:90:05", "0:6:054", 
      "0:006:54", "6:54", "2", "0:07:60", null);
  }
}
