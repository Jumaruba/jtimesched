package de.dominik_geyer.jtimesched.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class ProjectTest {

  public static Stream<Arguments> genSetSecondsToday(){
      return Stream.of(
              Arguments.of(Integer.MIN_VALUE, 0),
              Arguments.of(-100, 0),
              Arguments.of(-60,0),
              Arguments.of(-5,0),
              Arguments.of(0,0),
              Arguments.of(5, 5),
              Arguments.of(60, 60),
              Arguments.of(100,100),
              Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE)
      );
  }

  @ParameterizedTest
  @MethodSource("genSetSecondsToday")
  public void setSecondsTodayTest(int seconds, int expected) {
    // Given
    Project project = new Project();
    // When
    project.setSecondsToday(seconds);
    // Then
    Assertions.assertEquals(expected, project.getSecondsToday());
  }
}
