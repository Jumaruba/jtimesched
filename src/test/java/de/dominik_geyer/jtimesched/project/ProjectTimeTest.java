package de.dominik_geyer.jtimesched.project;

import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.text.ParseException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProjectTimeTest {
  @ParameterizedTest
  @MethodSource("parseSecondsInvalidParams")
  public void parseSecondsInvalidTest(String argument) {
    assertThrows(
        ParseException.class, () -> ProjectTime.parseSeconds(argument));
  }

  @ParameterizedTest
  @MethodSource("parseSecondsValidParams")
  public void parseSecondsValidTest(String strTime, int expected) {
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

  static Stream<String> parseSecondsInvalidParams() {
    return Stream.of(
        "", "0:90:05", "0:6:054", "0:007:05", "6:54", "2", "0:07:60", "0:07:", "0::02", ":6:54", "2:02:-1", "2:-1:05", "-1:09:05", null);
  }

  static Stream<Arguments> parseSecondsValidParams() {
    return Stream.of(
      Arguments.of("22:5:48", 79548),
      Arguments.of("36:25:8", 131108)
        // Arguments.of("0:00:00", 0),
        // Arguments.of("0:00:05", 5),
        // Arguments.of("0:00:5", 5),
        // Arguments.of("0:15:48", 948),
        // Arguments.of("0:15:8", 908),
        // Arguments.of("0:5:8", 308),
        // Arguments.of("6:15:48", 22548),
        // Arguments.of("36:15:48", 130548)
      );
  }

  @ParameterizedTest
  @MethodSource("genFormatSeconds")
  public void formatSecondsTest(int s, String expected) {
    // Given 'seconds'

    // When
    String result = ProjectTime.formatSeconds(s);

    // Then
    assertEquals(expected, result);
  }

  public static Stream<Arguments> genFormatSeconds() {
    return Stream.of(
        Arguments.of(0, "0:00:00"),
        Arguments.of(5, "0:00:05"),
        Arguments.of(65, "0:01:05"),
        Arguments.of(4215, "1:10:15"),
        Arguments.of(90065, "25:01:05"),
        Arguments.of(-1, "0:00:00"));
  }
}
