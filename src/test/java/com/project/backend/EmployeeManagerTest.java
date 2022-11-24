package com.project.backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeManagerTest {
    private final static String EMPLOYEES_FILE_ROUTE = "src/test/resources/employees.json";
    private static EmployeeManager employeeManager;

    @BeforeAll
    public static void setUp() throws IOException, InvalidJsonFileException {
        EmployeeFile repository = new EmployeeFile(EMPLOYEES_FILE_ROUTE);
        employeeManager = new EmployeeManager(repository);
        employeeManager.loadEmployeesFromJson();
    }

    @Test
    @DisplayName("Test that the employees are loaded from the employee.json file")
    public void testEmployeeLoad() {
        assertEquals(3, employeeManager.getEmployeesAsString().size());
    }

    @Test
    @DisplayName("Test that an employee can be modified and saved to the employee.json file")
    public void testEmployeeModify() throws IOException {
        Employee employee = employeeManager.getEmployees().get(0);
        employee.setFirstName("John");
        boolean modifiedSuccessfully = employeeManager.modifyEmployeeById(employee.getId(), employee);
        employee = employeeManager.getEmployees().get(0);

        assertTrue(modifiedSuccessfully);
        assertEquals(employee.getFirstName(), "John");
    }
}
