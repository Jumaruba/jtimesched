package de.dominik_geyer.jtimesched.project;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringWriter;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ProjectSerializerTest {
  private final String xmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  
  @Test
  public void newElementWithAttributesTest() throws TransformerConfigurationException {
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
  public void newElementWithAttributesAndTextTest() throws TransformerConfigurationException {
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
    final String expected = xmlProlog + "<color r=\"24\" g=\"165\" b=\"67\">#18a542</color>";
    assertEquals(writer.toString(), expected);
  }

  @Test
  public void newElementWithAttributesAndLongTest() throws TransformerConfigurationException {
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
    final String expected = xmlProlog + "<time format=\"seconds\">1664828078692</time>";
    assertEquals(writer.toString(), expected);
  }

  @Test
  public void newEmptyElementTest() throws TransformerConfigurationException {
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

  @Test
  public void nestedElementsTest() throws TransformerConfigurationException {
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el = "createdAt";
    Object data = 1664828078692L;

    try {
      // When
      hd.startElement("", "", "project", new AttributesImpl());
      ProjectSerializer.addXmlElement(hd, el, null, data);
      hd.endElement("", "", "project");
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }
    
    final String expected = xmlProlog + "<project><createdAt>1664828078692</createdAt></project>";
    assertEquals(writer.toString(), expected);
  }

  @Test
  public void emptyElementTest() throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    String el = "";

    // When and Then
    assertThrows(Exception.class, () -> ProjectSerializer.addXmlElement(hd, el, null, null));
  }

  @Test
  public void nullTransformerTest() throws TransformerConfigurationException {
    // Given
    TransformerHandler hd = getTransformerHandler();
    StringWriter writer = getWriter(hd);
    String el = "name";
    AttributesImpl atts = new AttributesImpl();
    atts.addAttribute("", "", "hello", "String", "4");
    Object data = "Hello";

    try {
      // When
      ProjectSerializer.addXmlElement(hd, el, atts, data);
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }
    
    // Then
    final String expected = xmlProlog + "<name hello=\"4\">Hello</name>";
    System.out.println(writer.toString());
    assertEquals(writer.toString(), expected);
  }


  // Auxiliary methods

  public TransformerHandler getTransformerHandler() throws TransformerConfigurationException {
    SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
    return tf.newTransformerHandler();
  }

  public StringWriter getWriter(TransformerHandler hd) {
    StringWriter writer = new StringWriter();
    hd.setResult(new StreamResult(writer));
    return writer;
  }
}
