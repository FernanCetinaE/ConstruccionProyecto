package com.project.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    List<Employee> employees;

    public List<Employee> getEmployees() {
        if (employees == null) {
            throw new RuntimeException("Employees list is null, please load the file first");
        }
        return employees;
    }

    public void loadEmployeesFromJson(String fileRoute) throws IOException, InvalidJsonFileException {
        employees = new ArrayList<>();

        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.loadFile(fileRoute);
        List<String[]> parsedObjects = employeeFile.parseJsonStructure();
        for (String[] parsedObject : parsedObjects) {
            Employee employee = new Employee(
                    parsedObject[0],
                    parsedObject[1],
                    parsedObject[2],
                    parsedObject[3]
            );
            employees.add(employee);
        }
    }
}
