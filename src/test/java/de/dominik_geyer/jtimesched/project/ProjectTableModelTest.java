package de.dominik_geyer.jtimesched.project;

import de.dominik_geyer.jtimesched.gui.table.ProjectTable;
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

  public void isEditableTemplate(boolean isRunning, int column, boolean expected){
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(isRunning);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertEquals(cellEditable, expected);
  }

  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_CHECK,
          ProjectTableModel.COLUMN_TITLE,
          ProjectTableModel.COLUMN_COLOR,
          ProjectTableModel.COLUMN_CREATED,
          ProjectTableModel.COLUMN_TIMEOVERALL,
          ProjectTableModel.COLUMN_TIMETODAY})
  @Tag("not_running")
  public void isCellEditableRunningFalse1(int column) {
    isEditableTemplate(false, column, true);
  }

  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_ACTION_DELETE,
          ProjectTableModel.COLUMN_ACTION_STARTPAUSE,
          8})
  @Tag("not_running")
  public void isCellEditableRunningFalse2(int column){
    isEditableTemplate(false, column, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {9,10,Integer.MAX_VALUE})
  @Tag("not_running")
  public void isCellEditableRunningFalse3(int column){
    isEditableTemplate(false, column, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1,-2,-8,Integer.MIN_VALUE})
  @Tag("not_running")
  public void isCellEditableRunningFalse4(int column) {
    isEditableTemplate(false, column, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_CHECK,
          ProjectTableModel.COLUMN_TITLE,
          ProjectTableModel.COLUMN_COLOR,
          ProjectTableModel.COLUMN_CREATED })
  @Tag("running")
  public void isCellEditableRunningTrue1(int column) {
    isEditableTemplate(true, column, true);
  }


  @ParameterizedTest
  @ValueSource(ints = {
          ProjectTableModel.COLUMN_TIMEOVERALL,
          ProjectTableModel.COLUMN_TIMETODAY,
          ProjectTableModel.COLUMN_ACTION_DELETE,
          ProjectTableModel.COLUMN_ACTION_STARTPAUSE,
          8
  })
  @Tag("running")
  public void isCellEditableRunningTrue2(int column) {
    isEditableTemplate(true, column, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {9,10,Integer.MAX_VALUE})
  @Tag("running")
  public void isCellEditableRunningTrue3(int column){
    isEditableTemplate(true, column, false);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1,-2,-8,Integer.MIN_VALUE})
  @Tag("running")
  public void isCellEditableRunningTrue4(int column){
    isEditableTemplate(true, column, false);
  }

}


