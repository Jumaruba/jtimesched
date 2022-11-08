package de.dominik_geyer.jtimesched.project;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Date;
import java.awt.Color;
import de.dominik_geyer.jtimesched.JTimeSchedApp;

public class ProjectTableModelTest {
  public static ProjectTableModel projectTableModel;

  @BeforeEach
  public void initProjectTableModel() {
    Project project = new Project();
    List<Project> arProj = new ArrayList<>();
    arProj.add(project);
    projectTableModel = new ProjectTableModel(arProj);
  }

  @AfterEach
  public void setNullProjectTableModel() {
    projectTableModel = null;
  }

  public void isEditableTemplate(
      boolean isRunning, int column, boolean expected) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(isRunning);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertEquals(cellEditable, expected);
  }

  @ParameterizedTest
  @ValueSource(ints = {-2, -1, 0})
  public void testPartitionE1(int col) {
    isEditableTemplate(false, col, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6})
  public void testPartitionE2(int col) {
    isEditableTemplate(false, col, true);
  }

  @ParameterizedTest
  @ValueSource(ints = {7, 8, 9})
  public void testPartitionE3(int col) {
    isEditableTemplate(false, col, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -2, -3})
  public void testPartitionE4(int col) {
    isEditableTemplate(true, col, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4})
  public void testPartitionE5(int col) {
    isEditableTemplate(true, col, true);
  }

  @ParameterizedTest
  @ValueSource(ints = {5, 6, 7, 8, 9})
  public void testPartitionE6(int col) {
    isEditableTemplate(true, col, false);
  }

  public void testRowParameter(int row) {
    try {
      // Given
      // row

      // When
      projectTableModel.isCellEditable(row, 0);

      // Then
      // accept
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getClass());
      Assertions.fail("This test should not return this kind of exception");
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-2, -1})
  public void testPartitionE7(int row) {
    testRowParameter(row);
  }

  @Test
  public void testPartitionE8() {
    testRowParameter(0);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  public void testPartitionE9(int row) {
    testRowParameter(row);
  }

  // Assignemnt 5

  @Test
  public void testGetColumnCount() {
    // When
    int columnCount = projectTableModel.getColumnCount();

    // Then
    Assertions.assertEquals(columnCount, 8);
  }

  @Test
  public void testGetRowCount() {
    // When
    int rowCount = projectTableModel.getRowCount();

    // Then
    Assertions.assertEquals(rowCount, 1);
  }

  @Test
  public void testGetColumnName() {
    // When
    String columnName = projectTableModel.getColumnName(2);

    // Then
    Assertions.assertEquals(columnName, "Title");
  }

  public static Stream<Arguments> genGetColumnClass() {
    return Stream.of(
        Arguments.of(ProjectTableModel.COLUMN_COLOR, Color.class),
        Arguments.of(ProjectTableModel.COLUMN_CREATED, Date.class),
        Arguments.of(ProjectTableModel.COLUMN_TIMEOVERALL, Integer.class),
        Arguments.of(ProjectTableModel.COLUMN_TIMETODAY, Integer.class),
        Arguments.of(ProjectTableModel.COLUMN_CHECK, Boolean.class),
        Arguments.of(ProjectTableModel.COLUMN_ACTION_DELETE, Boolean.class),
        Arguments.of(ProjectTableModel.COLUMN_ACTION_STARTPAUSE, Boolean.class),
        Arguments.of(8, String.class));
  }

  @ParameterizedTest
  @MethodSource("genGetColumnClass")
  public void testGetColumnClass(int value, Class expected) {
    // When
    Class columnClass = projectTableModel.getColumnClass(value);

    // Then
    Assertions.assertEquals(expected, columnClass);
  }
}
