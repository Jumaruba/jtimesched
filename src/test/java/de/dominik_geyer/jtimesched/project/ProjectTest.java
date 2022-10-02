package de.dominik_geyer.jtimesched.project;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ProjectTest {

  // Test setRunning and getRunning first, setSecondsOverall, getSecondsOverall
  @Test
    public void pauseRunningTest() throws ProjectException {
        //TODO: Diana (acabar)
        // Given
        Project project = new Project();
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

  @Test
  public void setSecondsTodayTest() {
    // TODO: Xuliane
  }

}
