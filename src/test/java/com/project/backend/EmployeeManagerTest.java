package com.project.backend;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeManagerTest {
    private final static String BASE_PATH = "src/test/resources/employees.json";
    private final static String EMPLOYEES_FILE_ROUTE = "src/test/resources/employees_copy.json";
    private static EmployeeManager employeeManager;

    @BeforeEach
    public  void setUp() throws IOException, InvalidJsonFileException {
        Files.copy(Paths.get(BASE_PATH), Paths.get(EMPLOYEES_FILE_ROUTE), StandardCopyOption.REPLACE_EXISTING);

        EmployeeFile repository = new EmployeeFile(EMPLOYEES_FILE_ROUTE);
        employeeManager = new EmployeeManager(repository);
        employeeManager.loadEmployeesFromJson();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        Files.delete(Paths.get(EMPLOYEES_FILE_ROUTE));
    }

    @Test
    @DisplayName("Test that the employees are loaded from the employee.json file")
    public void testEmployeeLoad() {
        assertEquals(3, employeeManager.getEmployeesAsString().size());
    }

    @Test
    @DisplayName("Test that an employee can be modified and saved to the employee.json file")
    public void testEmployeeModify() throws IOException {
        Employee employee = new Employee("1", "John", "Doe", "https://www.google.com");
        employee.setFirstName("John");
        assertTrue(employeeManager.modifyEmployeeById("1", employee));
        assertEquals("John", employeeManager.getEmployees().get(0).getFirstName());

        employee.setFirstName("Jane");
        assertTrue(employeeManager.modifyEmployeeById("1", employee));
        assertEquals("Jane", employeeManager.getEmployees().get(0).getFirstName());
    }

    @Test
    @DisplayName("Test that an employee can be deleted and update the employee.json file")
    public void testEmployeeDelete() throws IOException {
        assertEquals(3, employeeManager.getEmployeesAsString().size());
        assertTrue(employeeManager.deleteEmployeeById("1"));
        assertEquals(2, employeeManager.getEmployeesAsString().size());
    }


    @Test
    @DisplayName("Test that a new employee can be added and saved to the employee.json file")
    public void testEmployeeAdd() throws IOException {
        assertEquals(3, employeeManager.getEmployeesAsString().size());
        Employee newEmployee = employeeManager.addNewEmployee("John", "Doe", "https://www.google.com");
        assertEquals(4, employeeManager.getEmployeesAsString().size());
        assertEquals(newEmployee, employeeManager.getEmployees().get(3));
    }
}
