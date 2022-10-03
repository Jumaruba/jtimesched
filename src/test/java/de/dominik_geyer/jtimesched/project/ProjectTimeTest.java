package de.dominik_geyer.jtimesched.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ProjectTimeTest {
  /**
   * Test for valid time and invalid?
   */
  @ParameterizedTest
  @MethodSource("parseSecondsParams")
  public void parseSecondsTest(int s) {
    // TODO: Diana
  }

  public Stream<Integer> parseSecondsParams() {
    return Stream.of(1);
  }

  @Test
  public void formatZeroTimeTest() {
    // Given
    int seconds = 0;

    // When
    String result = ProjectTime.formatSeconds(seconds);

    // Then
    assertEquals("0:00:00", result);
  }

  @Test
  public void formatSecondsTest() {
    // Given
    int seconds = 5;

    // When
    String result = ProjectTime.formatSeconds(seconds);

    // Then
    assertEquals("0:00:05", result);
  }

  @Test
  public void formatMinutesTest() {
    // Given
    int seconds = 65;

    // When
    String result = ProjectTime.formatSeconds(seconds);

    // Then
    assertEquals("0:01:05", result);
  }

  @Test
  public void formatHoursTest() {
    // Given
    int seconds = 4215;

    // When
    String result = ProjectTime.formatSeconds(seconds);

    // Then
    assertEquals("1:10:15", result);
  }

  @Test
  public void formatDaysTest() {
    // Given
    int seconds = 90065;

    // When
    String result = ProjectTime.formatSeconds(seconds);

    // Then
    assertEquals("25:01:05", result);
  }

  @Test
  public void formatNegativeTest() {
    // Given
    int seconds = -1;

    // When
    String result = ProjectTime.formatSeconds(seconds);

    // Then
    assertEquals("0:00:00", result);
  }

}
