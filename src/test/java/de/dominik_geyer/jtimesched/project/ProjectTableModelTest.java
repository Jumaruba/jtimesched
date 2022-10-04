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


  @DisplayName("Not running - Editable columns")
  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_ACTION_DELETE,
          ProjectTableModel.COLUMN_TITLE,
          ProjectTableModel.COLUMN_COLOR,
          ProjectTableModel.COLUMN_CREATED,
          ProjectTableModel.COLUMN_TIMEOVERALL,
          ProjectTableModel.COLUMN_TIMETODAY,
          ProjectTableModel.COLUMN_ACTION_STARTPAUSE})
  public void isCellEditable1Test(int column) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(false);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertTrue(cellEditable);
  }

  @DisplayName("Running - Editable columns")
  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_ACTION_DELETE,
          ProjectTableModel.COLUMN_TITLE,
          ProjectTableModel.COLUMN_COLOR,
          ProjectTableModel.COLUMN_CREATED,
          ProjectTableModel.COLUMN_ACTION_STARTPAUSE
  })
  public void isCellEditable2Test(int column) {
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(true);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertTrue(cellEditable);
  }


  @DisplayName("Running - Non editable columns")
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
}
