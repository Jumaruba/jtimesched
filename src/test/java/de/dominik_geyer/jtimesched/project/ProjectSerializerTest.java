package de.dominik_geyer.jtimesched.project;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.awt.Color;

public class ProjectSerializerTest {
  private final String xmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private static String outputDir = "docs/05_assignment/outputDir/";
  private static ByteArrayOutputStream bytearr;
  private static TransformerHandler hd;

  public void setup() {
    try {
      SAXTransformerFactory tf =
          (SAXTransformerFactory) SAXTransformerFactory.newInstance();
      hd = tf.newTransformerHandler();
      bytearr = new ByteArrayOutputStream();
      OutputStreamWriter out = new OutputStreamWriter(bytearr, "UTF8");
      StreamResult streamResult = new StreamResult(out);
      hd.setResult(streamResult);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void finish() {
    try {
      hd.endDocument();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Document getDocument(String xml)
      throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(xml));
    return builder.parse(is);
  }

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

  // ASSIGNMENT 5 ==========================================================

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
    String expected =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><projects version=\"unknown\"/>";
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
    Project proj = new Project();
    Color color = new Color(219, 148, 112);
    proj.setColor(color);
    proj.setTitle("New Project");
    proj.setChecked(true);
    proj.setRunning(true);
    projects.add(proj);

    // When
    try {
      ps.writeXml(projects);
    } catch (Exception e) {
      fail("Unexpected exception");
    }
    String expected = getProjectsXml1();
    Pattern classPattern = Pattern.compile(expected);

    try {
      // Then
      String result = readProjectsFile();
      Matcher m = classPattern.matcher(result);
      assertTrue(m.matches());
    } catch (IOException e) {
      e.printStackTrace();
      fail("Shouldn't have thrown an exception");
    }
  }

  @Test
  public void noColorIdleUncheckedWriteXmlTest() {
    // Given
    ProjectSerializer ps =
        new ProjectSerializer(outputDir + "zeroProjectsTest");
    List<Project> projects = new ArrayList<Project>();
    Project proj = new Project();
    proj.setTitle("New Project");
    proj.setChecked(false);
    proj.setRunning(false);
    projects.add(proj);

    // When
    try {
      ps.writeXml(projects);
    } catch (Exception e) {
      fail("Unexpected exception");
    }
    String expected = getProjectsXml2();
    Pattern classPattern = Pattern.compile(expected);

    try {
      // Then
      String result = readProjectsFile();
      Matcher m = classPattern.matcher(result);
      assertTrue(m.matches());
    } catch (IOException e) {
      e.printStackTrace();
      fail("Shouldn't have thrown an exception");
    }
  }

  public String readProjectsFile() throws IOException {
    File file = new File(outputDir + "zeroProjectsTest");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st = "", tmp = "";

    while ((tmp = br.readLine()) != null) {
      if (tmp.trim() != "") {
        st += tmp.trim();
      }
    }
    br.close();

    return st;
  }

  public String getProjectsXml1() {
    StringBuilder sb = new StringBuilder();
    sb.append("<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>");
    sb.append("<projects version=\"unknown\">");
    sb.append("<project>");
    sb.append("<title>New Project</title>");
    sb.append("<notes/>");
    sb.append("<created>\\d+</created>");
    sb.append("<started>\\d+</started>");
    sb.append("<running>no</running>");
    sb.append("<checked>yes</checked>");
    sb.append("<time overall=\"\\d+\" today=\"\\d+\"/>");
    sb.append("<quota overall=\"0\" today=\"0\"/>");
    sb.append("<color red=\"219\" green=\"148\" blue=\"112\" alpha=\"255\"/>");
    sb.append("</project>");
    sb.append("</projects>");
    return sb.toString();
  }

  public String getProjectsXml2() {
    StringBuilder sb = new StringBuilder();
    sb.append("<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>");
    sb.append("<projects version=\"unknown\">");
    sb.append("<project>");
    sb.append("<title>New Project</title>");
    sb.append("<notes/>");
    sb.append("<created>\\d+</created>");
    sb.append("<started>\\d+</started>");
    sb.append("<running>no</running>");
    sb.append("<checked>no</checked>");
    sb.append("<time overall=\"0\" today=\"0\"/>");
    sb.append("<quota overall=\"0\" today=\"0\"/>");
    sb.append("</project>");
    sb.append("</projects>");
    return sb.toString();
  }

  @Test
  public void testGetEndXmlElement() {
    try {
      // Given
      setup();
      // When
      hd.startDocument();
      ProjectSerializer.endXmlElement(hd, "projects");
      hd.endDocument();

      // Then
      Assertions.assertEquals(
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?></projects>",
          new String(bytearr.toByteArray()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void addXmlElement_1() {
    try {
      // Given
      setup();
      String element = "title";

      // When
      hd.startDocument();
      ProjectSerializer.addXmlElement(hd, element, null, null);
      hd.endDocument();

      // Then
      Assertions.assertEquals(
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><title/>",
          new String(bytearr.toByteArray()));

    } catch (Exception e) {
      Assertions.fail();
      e.printStackTrace();
    }
  }

  @Test
  public void addXmlElement_2() {
    try {
      // Given
      setup();
      String element = "element";
      AttributesImpl atts = new AttributesImpl();
      String data = "title";

      // When
      hd.startDocument();
      ProjectSerializer.addXmlElement(hd, element, atts, data);
      hd.endDocument();

      // Then
      Assertions.assertEquals(
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><element>title</element>",
          new String(bytearr.toByteArray()));

    } catch (Exception e) {
      Assertions.fail();
      e.printStackTrace();
    }
  }

  @Test
  public void startXmlElement_1() {
    try {
      // Given
      setup();
      String element = "element";
      AttributesImpl atts = new AttributesImpl();

      // When
      hd.startDocument();
      ProjectSerializer.startXmlElement(hd, element, atts);
      hd.endDocument();

      // Then
      Assertions.assertEquals(
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><element>",
          new String(bytearr.toByteArray()));

    } catch (Exception e) {
      Assertions.fail();
      e.printStackTrace();
    }
  }

  @Test
  public void startXmlElement_2() {
    try {
      // Given
      setup();
      String element = "tag";

      // When
      hd.startDocument();
      ProjectSerializer.startXmlElement(hd, element, null);
      hd.endDocument();

      // Then
      Assertions.assertEquals(
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?><tag>",
          new String(bytearr.toByteArray()));

    } catch (Exception e) {
      Assertions.fail();
      e.printStackTrace();
    }
  }

  @Test
  public void testAddXmlAttribute() {
    try {
      // Given
      String attribute = "title";
      AttributesImpl atts = new AttributesImpl();
      String data = "my-title";

      // When
      ProjectSerializer.addXmlAttribute(atts, attribute, data);

      // Then
      Assertions.assertEquals("my-title", atts.getValue("title"));
    } catch (Exception e) {
      Assertions.fail();
      e.printStackTrace();
    }
  }

  @Test
  public void testGetFirstElement() {
    try {
      // Given
      ProjectSerializer projSerializer = new ProjectSerializer("");
      Document doc =
          getDocument("<tag><tag1><tag2>another tag</tag2></tag1></tag>");
      Element root = doc.getDocumentElement();

      // When

      Node firstElement = (Node) projSerializer.getFirstElement(root, "tag1");
      String nodeName = firstElement.getNodeName();
      String nodeValue = firstElement.getNodeValue();

      // Then
      Assertions.assertEquals("tag1", nodeName);
      Assertions.assertEquals(null, nodeValue);

    } catch (Exception e) {
      Assertions.fail();
      e.printStackTrace();
    }
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "docs/05_assignment/inputDir/projectTest",
      })
  public void readXml(String filepath)
      throws ParserConfigurationException, SAXException, IOException {
    ProjectSerializer p = new ProjectSerializer(filepath);
    ArrayList<Project> projectList = p.readXml();

    // Then
    Assertions.assertEquals(2, projectList.size());

    Assertions.assertEquals("", projectList.get(0).getNotes());
    Assertions.assertEquals("", projectList.get(0).getTitle());
    Assertions.assertEquals(
        "Wed Nov 09 14:59:58 WET 2022",
        projectList.get(0).getTimeCreated().toString());
    Assertions.assertEquals(
        "Wed Nov 09 14:59:58 WET 2022",
        projectList.get(0).getTimeStart().toString());
    Assertions.assertEquals(false, projectList.get(0).isRunning());
    Assertions.assertEquals(false, projectList.get(0).isChecked());
    Assertions.assertEquals(0, projectList.get(0).getSecondsOverall());
    Assertions.assertEquals(0, projectList.get(0).getSecondsToday());
    Assertions.assertEquals(0, projectList.get(0).getQuotaOverall());
    Assertions.assertEquals(0, projectList.get(0).getQuotaToday());
    Assertions.assertEquals(null, projectList.get(0).getColor());

    Assertions.assertEquals("New project", projectList.get(1).getTitle());
    Assertions.assertEquals("A nice note", projectList.get(1).getNotes());
    Assertions.assertEquals(true, projectList.get(1).isRunning());
    Assertions.assertEquals(true, projectList.get(1).isChecked());
    Assertions.assertEquals(0, projectList.get(1).getQuotaOverall());
    Assertions.assertEquals(600, projectList.get(1).getQuotaToday());
    Assertions.assertEquals(
        new Color(122, 194, 229, 255), projectList.get(1).getColor());
  }
}
