package de.dominik_geyer.jtimesched;

import java.io.File;
import java.io.IOException;

import org.codehaus.plexus.util.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JTimeSchedAppTest {
  @Test
  public void testConfFolder() {
    // Given
    File conf = new File(JTimeSchedApp.CONF_PATH);
    try {
      FileUtils.deleteDirectory(conf);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assertions.assertFalse(conf.exists());

    // When
    JTimeSchedApp.main(null);

    // Then
    Assertions.assertTrue(conf.exists());
  }
}
