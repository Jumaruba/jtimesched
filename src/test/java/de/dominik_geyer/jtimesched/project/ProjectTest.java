package de.dominik_geyer.jtimesched.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

public class ProjectTest {

  public static Stream<Arguments> genSetSecondsToday() {
    return Stream.of(
        Arguments.of(Integer.MIN_VALUE, 0),
        Arguments.of(-100, 0),
        Arguments.of(-60, 0),
        Arguments.of(-5, 0),
        Arguments.of(0, 0),
        Arguments.of(5, 5),
        Arguments.of(60, 60),
        Arguments.of(100, 100),
        Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE));
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

  // Assignment 5
  @ParameterizedTest
  @MethodSource("genSecondsOverall")
  public void setSecondsOverallTest(int secondsOverall, int expected) {
    // Given
    Project project = new Project();
    // When
    project.setSecondsOverall(secondsOverall);
    // Then
    Assertions.assertEquals(expected, project.getSecondsOverall());
  }

  public static Stream<Arguments> genSecondsOverall() {
    return Stream.of(Arguments.of(-2, 0), Arguments.of(10, 10));
  }

  @Test
  public void setQuotaTodayTest() {
    // Given
    Project project = new Project();
    int quotaToday = 5;

    // When
    project.setQuotaToday(quotaToday);
    // Then
    Assertions.assertEquals(5, project.getQuotaToday());
  }

  @Test
  public void setQuotaOverallTest() {
    // Given
    Project project = new Project();
    int quotaOverall = 5;

    // When
    project.setQuotaOverall(quotaOverall);
    // Then
    Assertions.assertEquals(5, project.getQuotaOverall());
  }

  @ParameterizedTest
  @MethodSource("genAdjustSecondsToday")
  public void adjustSecondsTodayTest(
      int secondsToday,
      int oldSecondsToday,
      int oldSecondsOverall,
      int expectedSecondsToday,
      int expectedSecondsOverall) {
    // Given
    Project project = new Project();
    project.setSecondsToday(oldSecondsToday);
    project.setSecondsOverall(oldSecondsOverall);

    // When
    project.adjustSecondsToday(secondsToday);

    // Then
    Assertions.assertEquals(expectedSecondsToday, project.getSecondsToday());
    Assertions.assertEquals(
        expectedSecondsOverall, project.getSecondsOverall());
  }

  public static Stream<Arguments> genAdjustSecondsToday() {
    return Stream.of(
        Arguments.of(-2, 10, 43, 0, 33), Arguments.of(16, 10, 43, 16, 49));
  }

  @Test
  public void resetTodayTest() {
    // Given
    Project project = new Project();

    // When
    project.resetToday();

    Assertions.assertEquals(0, project.getSecondsToday());
    Assertions.assertEquals(0, project.getQuotaToday());
    // TODO: test date with mock?
  }

  @Test
  public void newProjectToStringTest() {
    // Given
    Project project = new Project();
    project.setTitle("New Project");

    // When
    String result = project.toString();

    String expected =
        "Project [title=New Project, running=no, secondsOverall=0, secondsToday=0, checked=no]";
    Assertions.assertEquals(expected, result);
  }

  @Test
  public void runningAndCheckedProjectToStringTest() {
    // Given
    Project project = new Project();
    project.setTitle("New Project");
    project.setRunning(true);
    project.setChecked(true);

    // When
    String result = project.toString();
    String expected =
        "Project [title=New Project, running=yes, secondsOverall=0, secondsToday=0, checked=yes]";
    Assertions.assertEquals(expected, result);
  }

  @Test
  public void idleElapsedSecondsTest() {
    // Given
    Project project = new Project();

    // When and Then
    Assertions.assertThrows(
        ProjectException.class, () -> project.getElapsedSeconds());
  }

  @Test
  public void runningElapsedSecondsTest() {
    // Given
    Project project = new Project();
    project.setRunning(true);

    // When and Then
    try {
      Assertions.assertEquals(0, project.getElapsedSeconds());
    } catch (ProjectException e) {
      fail("Exception shouldn't be thrown");
    }

    // TODO: don't know how to test it better because it uses the current time
  }

  @Test
  public void idlePauseTest() {
    // Given
    Project project = new Project();

    // When and Then
    Assertions.assertThrows(ProjectException.class, () -> project.pause());
  }

  @Test
  public void runningPauseTest() {
    // Given
    Project project = new Project();

    // When and Then
    try {
      project.start();
      Thread.sleep(2000);
      project.pause();

      Assertions.assertEquals(2, project.getSecondsOverall());
      Assertions.assertEquals(2, project.getSecondsToday());
      Assertions.assertEquals(false, project.isRunning());
    } catch (ProjectException e) {
      fail("Exception shouldn't be thrown");
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    // TODO: don't know how to test it better because it uses the current time
  }

  // public void pause() throws ProjectException {
  //   if (!this.isRunning()) throw new ProjectException("Timer not running");

  //   int secondsElapsed = this.getElapsedSeconds();
  //   this.secondsOverall += secondsElapsed;
  //   this.secondsToday += secondsElapsed;

  //   this.running = false;
  // }

}
