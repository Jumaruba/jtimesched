package de.dominik_geyer.jtimesched.misc;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PlainTextFormatterTest {
  PlainTextFormatter ptf = new PlainTextFormatter();

  @Test
  public void testFormatter() {
    Level level = Level.ALL;
    LogRecord log = new LogRecord(level, "This is a log");
    long millis = 1668014664908l;
    log.setMillis(millis);

    String result = ptf.format(log);

    String date = "2022-11-09";
    String weekDay = "quarta";
    String time = "17:24:24";
    System.out.println(result);
    String expected =
        String.format("%s (%s) %s [ALL]: This is a log\n", date, weekDay, time);
    Assertions.assertEquals(expected, result);
  }
}
