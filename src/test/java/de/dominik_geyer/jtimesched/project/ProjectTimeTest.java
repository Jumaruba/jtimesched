package de.dominik_geyer.jtimesched.project;

import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.jupiter.api.Assertions;
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
        "", "0:90:05", "0:6:054", "0:006:54", "6:54", "2", "0:07:60", null);
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


  public void testFormatSeconds(int s, String expected){
    // Given 'seconds'

    // When
    String result = ProjectTime.formatSeconds(s);

    // Then
    Assertions.assertEquals(expected, result);
  }

  @ParameterizedTest
  @MethodSource("genFormatSecondsPartition")
  public void formatSecondsPartitionTest(int s, String expected) {
    testFormatSeconds(s, expected);
  }

  @ParameterizedTest
  @MethodSource("genFormatSecondsBoundary")
  public void formatSecondsBoundary(int s, String expected) {
    testFormatSeconds(s, expected);
  }
  public static Stream<Arguments> genFormatSecondsPartition() {
    return Stream.of(
        Arguments.of(-2, "0:00:00"),
        Arguments.of(0, "0:00:00"),
        Arguments.of(5, "0:00:05"),
        Arguments.of(65, "0:01:05"),
        Arguments.of(4215, "1:10:15"),
        Arguments.of(90065, "25:01:05"));
  }

  public static Stream<Arguments> genFormatSecondsBoundary() {
    return Stream.of(
        Arguments.of(-1, "0:00:00"),
        Arguments.of(0, "0:00:00"),
        Arguments.of(59, "0:00:59"),
        Arguments.of(60, "0:01:00"),
        Arguments.of(3599, "0:59:59"),
        Arguments.of(3600, "1:00:00"),
        Arguments.of(86399, "23:59:59"),
        Arguments.of(86400, "24:00:00"));
  }


}
