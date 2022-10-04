package de.dominik_geyer.jtimesched.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringWriter;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ProjectSerializerTest {
  private final String xmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  @Test
  public void addXmlElementTest() throws TransformerConfigurationException {
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
