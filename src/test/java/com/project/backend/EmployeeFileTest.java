package com.project.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeFileTest {
    private final static String TEST_FILE_ROUTE = "src/test/resources/test.txt";
    private final static String FILE_ROUTE = "src/test/resources/employees.json";
    private final static String INVALID_FILE_ROUTE = "src/test/resources/invalid_employees.json";

    @Test
    @DisplayName("Test improper access to the file's content")
    public void testFileAccess() {
        EmployeeFile employeeFile = new EmployeeFile();
        assertThrows(RuntimeException.class, employeeFile::getFileContent);
    }

    @Test
    @DisplayName("Test if the test.txt file content is read correctly")
    public void testFileContent() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.readFile(TEST_FILE_ROUTE);
        assertEquals("Hello, world!", employeeFile.getFileContent());
    }

    @Test
    @DisplayName("Test if the employees.json file is read correctly")
    public void readEmployeeFileContent() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.readFile(FILE_ROUTE);
        String fileContent = employeeFile.getFileContent();
        assertNotNull(fileContent);
        assertFalse(fileContent.isEmpty());
    }

    @Test
    @DisplayName("Test that the JSON structure is valid")
    public void validateJSONStructure() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.readFile(FILE_ROUTE);
        assertTrue(employeeFile.validateJSONStructure());
    }

    @Test
    @DisplayName("Test that the JSON structure is invalid")
    public void validateInvalidJSONStructure() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.readFile(INVALID_FILE_ROUTE);
        assertFalse(employeeFile.validateJSONStructure());
    }
}
