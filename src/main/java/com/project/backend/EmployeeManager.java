package com.project.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeManager {
    private final EmployeeFile repository;
    private final List<Employee> employees;

    public EmployeeManager(EmployeeFile repository) {
        this.repository = repository;
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<String> getEmployeesAsString() {
        return employees.stream().map(Employee::toString).collect(Collectors.toList());
    }

    public void loadEmployeesFromJson() throws IOException, InvalidJsonFileException {
        employees.clear();

        String rawJsonContent = repository.loadFile();
        List<String[]> parsedObjects = repository.parseJsonStructure(rawJsonContent);
        for (String[] parsedObject : parsedObjects) {
            Employee employee = new Employee(parsedObject[0], parsedObject[1], parsedObject[2], parsedObject[3]);
            employees.add(employee);
        }
    }

    public boolean modifyEmployeeById(String employeeId, Employee newData) throws IOException {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employeeId)) {
                employees.set(i, newData);
                repository.saveToFile(employees);
                return true;
            }
        }
        return false;
    }
}
