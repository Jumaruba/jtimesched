package de.dominik_geyer.jtimesched.project;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

public class ProjectTest {
  private Project project;

  @BeforeEach
  void setUp() {
    project = new Project();
  }

  // Test setRunning and getRunning first, setSecondsOverall, getSecondsOverall
  @Test
  public void pauseRunningTest() throws ProjectException {
    //TODO: Diana (acabar)
    // Given
    Project projectSpy = Mockito.spy(project);
    projectSpy.setRunning(true);
    Mockito.when(projectSpy.getElapsedSeconds()).thenReturn(2);
    projectSpy.setSecondsOverall(0);
    projectSpy.pause();
    Assert.assertEquals(projectSpy.getSecondsOverall(), 2);
    Assert.assertEquals(projectSpy.getSecondsToday(), 2);
    Assert.assertFalse(projectSpy.isRunning());
  }

  @Test
  public void pauseNotRunningTest() {
    // TODO: assertThrows
    // TODO: Diana (acabar)
  }


  public static Stream<Arguments> genSeconds(){
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
  @MethodSource("genSeconds")
  public void setSecondsTodayPositiveTest(int seconds, int expected) {
    // Given
    Project project = new Project();
    // When
    project.setSecondsToday(seconds);
    // Then
    Assertions.assertEquals(expected, project.getSecondsToday());
  }

}
