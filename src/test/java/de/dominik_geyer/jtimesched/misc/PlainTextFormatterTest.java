package de.dominik_geyer.jtimesched.misc;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
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

    Locale.setDefault(Locale.Category.FORMAT, Locale.ROOT);

    SimpleDateFormat sdf = new SimpleDateFormat("E");
    Date dt = new Date(millis);
    String weekDay = sdf.format(dt);
    String date = "2022-11-09";
    String time = "17:24:24";

    String result = ptf.format(log);
    String expected; 
    if (System.getProperty("os.name").split(" ")[0].equals("Windows")) { 
      expected = String.format("%s (%s) %s [ALL]: This is a log\r\n", date, weekDay, time);
    } else {
      expected = String.format("%s (%s) %s [ALL]: This is a log\n", date, weekDay, time);
    }

      Assertions.assertEquals(expected, result); 
  }
}

