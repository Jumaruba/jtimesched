package de.dominik_geyer.jtimesched.project;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

public class ProjectTableModelTest {

  public static ProjectTableModel projectTableModel;
  @BeforeEach
  public void initProjectTableModel(){
    Project project = new Project();
    List<Project> arProj = new ArrayList<>();
    arProj.add(project);
    projectTableModel = new ProjectTableModel(arProj);
  }

  @AfterEach
  public void setNullProjectTableModel(){
    projectTableModel = null;
  }

  @DisplayName("[NOT running] Editable columns")
  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_TITLE,
          ProjectTableModel.COLUMN_COLOR,
          ProjectTableModel.COLUMN_CREATED,
          ProjectTableModel.COLUMN_TIMEOVERALL,
          ProjectTableModel.COLUMN_TIMETODAY})
  public void isCellEditable1Test(int column) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(false);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertTrue(cellEditable);
  }

  @DisplayName("[NOT running] Out of range positive columns")
  @ParameterizedTest
  @ValueSource(ints = {8,9,10,Integer.MAX_VALUE})
  public void isCellEditable5Test(int column){
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(false);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertFalse(cellEditable);
  }

  @DisplayName("[NOT running] - Out of range negative columns")
  @ParameterizedTest
  @ValueSource(ints = {-1,-2,-8,Integer.MIN_VALUE})
  public void isCellEditable7Test(int column) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(false);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertFalse(cellEditable);

  }

  @DisplayName("[Running] Editable columns")
  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_TITLE,
          ProjectTableModel.COLUMN_COLOR,
          ProjectTableModel.COLUMN_CREATED })
  public void isCellEditable2Test(int column) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(true);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertTrue(cellEditable);
  }


  @DisplayName("[Running] Non editable columns")
  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_TIMEOVERALL,
          ProjectTableModel.COLUMN_TIMETODAY,
  })
  public void isCellEditable3Test(int column) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(true);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertFalse(cellEditable);
  }

  @DisplayName("[Running] Out of range positive columns")
  @ParameterizedTest
  @ValueSource(ints = {8,9,10,Integer.MAX_VALUE})
  public void isCellEditable4Test(int column){
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(true);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertFalse(cellEditable);
  }




  @DisplayName("[Running] Out of range negative columns")
  @ParameterizedTest
  @ValueSource(ints = {-1,-2,-8,Integer.MIN_VALUE})
  public void isCellEditable6Test(int column){
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(true);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertFalse(cellEditable);
  }

}


