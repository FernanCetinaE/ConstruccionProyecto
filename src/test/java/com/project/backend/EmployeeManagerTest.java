package com.project.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeManagerTest {
    private final static String EMPLOYEES_FILE_ROUTE = "src/test/resources/employees.json";

    @Test
    @DisplayName("Test that the employees are loaded from the employee.json file")
    public void testEmployeeLoad() throws InvalidJsonFileException, IOException {
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.loadEmployeesFromJson(EMPLOYEES_FILE_ROUTE);
        assertEquals(3, employeeManager.getEmployeesAsString().size());
    }
}
