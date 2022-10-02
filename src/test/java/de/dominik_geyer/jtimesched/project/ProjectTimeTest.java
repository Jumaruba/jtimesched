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
    String secondsStr = "0:00:00";
    assertEquals(ProjectTime.formatSeconds(0), secondsStr);
  }

  @Test
  public void formatSecondsTest() {
    String secondsStr = "0:00:05";
    assertEquals(ProjectTime.formatSeconds(5), secondsStr);
  }

  @Test
  public void formatMinutesTest() {
    String secondsStr = "0:01:05";
    assertEquals(ProjectTime.formatSeconds(65), secondsStr);
  }

  @Test
  public void formatHoursTest() {
    String secondsStr = "1:10:15";
    assertEquals(ProjectTime.formatSeconds(4215), secondsStr);
  }

  @Test
  public void formatDaysTest() {
    String secondsStr = "25:01:05";
    assertEquals(ProjectTime.formatSeconds(90065), secondsStr);
  }

  @Test
  public void formatNegativeTest() {
    String secondsStr = "0:00:00";
    assertEquals(ProjectTime.formatSeconds(-1), secondsStr);
  }

}
