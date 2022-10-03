package de.dominik_geyer.jtimesched.project;

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
  final String xmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";


  // TODO: define partitions in the docs and test for each partition
  @Test
  public void addXmlElementTest() throws TransformerConfigurationException {
    // Just an initial test to see if it is viable to test this function
    SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
    TransformerHandler hd = tf.newTransformerHandler();
    StringWriter writer = new StringWriter();
    hd.setResult(new StreamResult(writer));
    String el = "name";
    AttributesImpl atts = new AttributesImpl();
    atts.addAttribute("", "", "hello", "String", "4");
    Object data = "Hello";

    try {
      ProjectSerializer.addXmlElement(hd, el, atts, data);
    } catch (SAXException e) {
      fail("Exception should not be thrown");
    }
    
    final String expected = xmlProlog + "<name hello=\"4\">Hello</name>";
    assertEquals(writer.toString(), expected);
  }
}
