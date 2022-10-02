package de.dominik_geyer.jtimesched.project;

import java.util.stream.Stream;

import org.junit.Test;
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
  public void parseDateTest() {
    // TODO: Diana
  }

}
