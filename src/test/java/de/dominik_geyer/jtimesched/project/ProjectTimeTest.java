package de.dominik_geyer.jtimesched.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProjectTimeTest {
  @ParameterizedTest
  @MethodSource("parseSecondsParams")
  public void parseSecondsTest(int s) {
  }

  public Stream<Integer> parseSecondsParams() {
    return Stream.of(1);
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
      Arguments.of(5,"0:00:05"),
      Arguments.of(65,"0:01:05"),
      Arguments.of(4215,"1:10:15"),
      Arguments.of(90065,"25:01:05"),
      Arguments.of(-1,"0:00:00")
    );
  }

}
