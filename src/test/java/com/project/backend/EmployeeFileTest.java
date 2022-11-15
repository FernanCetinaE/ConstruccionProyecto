package com.project.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeFileTest {
    private final static String TEST_FILE_ROUTE = "src/test/resources/test.txt";
    private final static String EMPLOYEES_FILE_ROUTE = "src/test/resources/employees.json";
    private final static String INVALID_EMPLOYEES_FILE_ROUTE = "src/test/resources/invalid_employees.json";

    @Test
    @DisplayName("Test if the test.txt file content is read correctly")
    public void testFileContent() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.loadFile(TEST_FILE_ROUTE);
        assertEquals("Hello, world!", employeeFile.getRawContent());
    }

    @Test
    @DisplayName("Test if the employees.json file is read correctly")
    public void readEmployeeFileContent() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.loadFile(EMPLOYEES_FILE_ROUTE);
        String fileContent = employeeFile.getRawContent();
        assertNotNull(fileContent);
        assertFalse(fileContent.isEmpty());
    }

    @Test
    @DisplayName("Test that the JSON structure is valid")
    public void validateJSONStructure() throws IOException, InvalidJsonFileException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.loadFile(EMPLOYEES_FILE_ROUTE);
        List<String[]> parsedObjects = employeeFile.parseJsonStructure();
        assertEquals(3, parsedObjects.size());
    }

    @Test
    @DisplayName("Test that the JSON structure is invalid")
    public void validateInvalidJSONStructure() throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.loadFile(INVALID_EMPLOYEES_FILE_ROUTE);
        assertThrows(InvalidJsonFileException.class, employeeFile::parseJsonStructure);
    }
}
