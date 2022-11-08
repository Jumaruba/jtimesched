package de.dominik_geyer.jtimesched.project;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.awt.Color;

public class ProjectSerializerTest {
  private final String xmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private static String outputDir = "docs/05_assignment/outputDir/";

  // Simple element
  @Test
  public void elementWithAttributesTest()
      throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el = "time";
    AttributesImpl atts = new AttributesImpl();
    atts.addAttribute("", "", "overall", "", "4");
    try {
      // When
      ProjectSerializer.addXmlElement(hd, el, atts, null);
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }

    // Then
    final String expected = xmlProlog + "<time overall=\"4\"/>";
    assertEquals(writer.toString(), expected);
  }

  @Test
  public void elementWithAttributesAndTextTest()
      throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el = "color";
    AttributesImpl atts = new AttributesImpl();
    atts.addAttribute("", "", "r", "", "24");
    atts.addAttribute("", "", "g", "", "165");
    atts.addAttribute("", "", "b", "", "67");
    Object data = "#18a542";

    try {
      // When
      ProjectSerializer.addXmlElement(hd, el, atts, data);
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }

    // Then
    final String expected =
        xmlProlog + "<color r=\"24\" g=\"165\" b=\"67\">#18a542</color>";
    assertEquals(writer.toString(), expected);
  }

  @Test
  public void elementWithAttributesAndLongTest()
      throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el = "time";
    AttributesImpl atts = new AttributesImpl();
    atts.addAttribute("", "", "format", "", "seconds");
    Object data = 1664828078692L;

    try {
      // When
      ProjectSerializer.addXmlElement(hd, el, atts, data);
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }

    // Then
    final String expected =
        xmlProlog + "<time format=\"seconds\">1664828078692</time>";
    assertEquals(writer.toString(), expected);
  }

  @Test
  public void elementEmptyTest() throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el = "time";

    try {
      // When
      ProjectSerializer.addXmlElement(hd, el, null, null);
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }

    // Then
    final String expected = xmlProlog + "<time/>";
    assertEquals(writer.toString(), expected);
  }

  // Nested elements
  @Test
  public void nestedElementsWithAttributesTest()
      throws TransformerConfigurationException {
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el1 = "createdAt";
    String el2 = "startedAt";
    Object data1 = 1664828078692L;
    AttributesImpl atts2 = new AttributesImpl();
    atts2.addAttribute("", "", "value", "", "1664828011325");

    try {
      // When
      hd.startElement("", "", "project", new AttributesImpl());
      ProjectSerializer.addXmlElement(hd, el1, null, data1);
      ProjectSerializer.addXmlElement(hd, el2, atts2, null);
      hd.endElement("", "", "project");
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }

    final String expected =
        xmlProlog
            + "<project><createdAt>1664828078692</createdAt><startedAt value=\"1664828011325\"/></project>";
    assertEquals(writer.toString(), expected);
  }

  // Invalid partitions
  @Test
  public void nullTransformerTest() throws TransformerConfigurationException {
    // Given
    String el = "project";

    // When and Then
    assertThrows(
        NullPointerException.class,
        () -> ProjectSerializer.addXmlElement(null, el, null, null));
  }

  @Test
  public void unnamedElementTest()
      throws TransformerConfigurationException, SAXException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    String el = "";

    ProjectSerializer.addXmlElement(hd, el, null, null);

    // When and Then
    assertThrows(
        SAXException.class,
        () -> ProjectSerializer.addXmlElement(hd, el, null, null));
  }

  @Test
  public void nullElementTest() throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();

    // When and Then
    assertThrows(
        SAXException.class,
        () -> ProjectSerializer.addXmlElement(hd, null, null, null));
  }

  // Auxiliary methods
  public TransformerHandler getTransformerHandler()
      throws TransformerConfigurationException {
    SAXTransformerFactory tf =
        (SAXTransformerFactory) SAXTransformerFactory.newInstance();
    return tf.newTransformerHandler();
  }

  public StringWriter getWriter(TransformerHandler hd) {
    StringWriter writer = new StringWriter();
    hd.setResult(new StreamResult(writer));
    return writer;
  }

  // Assignment 5
  // writeXml
  @Test
  public void zeroProjectsWriteXmlTest() {
    // Given
    ProjectSerializer ps =
        new ProjectSerializer(outputDir + "zeroProjectsTest");
    List<Project> projects = new ArrayList<Project>();

    // When
    try {
      ps.writeXml(projects);
    } catch (Exception e) {
      fail("Unexpected exception");
    }

    // Then
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><projects version=\"unknown\"/>";
    try {
      assertEquals(expected, readProjectsFile());
    } catch (IOException e) {
      e.printStackTrace();
      fail("Shouldn't have thrown an exception");
    }
  }

  @Test
  public void colorRunningCheckedWriteXmlTest() {
    // Given
    ProjectSerializer ps =
        new ProjectSerializer(outputDir + "zeroProjectsTest");
    List<Project> projects = new ArrayList<Project>();

    // => color null, running, checked
  }

  @Test
  public void noColorIdleUncheckedWriteXmlTest() {
    // Given
    ProjectSerializer ps =
        new ProjectSerializer(outputDir + "zeroProjectsTest");
    List<Project> projects = new ArrayList<Project>();
    Project proj = new Project();
    Color color = Mockito.mock(Color.class);
    proj.setColor(color);
    proj.setChecked(true);
    proj.setRunning(true);
    
    projects.add(proj);

    // When

    // Then
  }

  public String readProjectsFile() throws IOException {
    File file = new File(outputDir + "zeroProjectsTest");
    System.out.println(file.getAbsolutePath());
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st = "", tmp = "";
    
    while ((tmp = br.readLine()) != null) {
      st += tmp;
    }

    return st;
  }
  
}