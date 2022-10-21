package de.dominik_geyer.jtimesched.project;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

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
    
    public void testRowParameter(int row){
        try {
            // Given
            // row

            // When
            projectTableModel.isCellEditable(row, 0);

            // Then
            // accept
        } catch (IndexOutOfBoundsException e){
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
}


