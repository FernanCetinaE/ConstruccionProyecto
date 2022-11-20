package com.project.backend;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFile {
    private final String fileRoute;

    public EmployeeFile(String fileRoute) {
        this.fileRoute = fileRoute;
    }

    public String loadFile() throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileRoute))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        }
        return fileContent.toString();
    }

    public void saveToFile(List<Employee> employees) throws IOException {
        JsonObject employeesObject = new JsonObject();
        JsonArray employeesArray = new JsonArray();
        for (Employee employee : employees) {
            JsonObject employeeObject = new JsonObject();
            employeeObject.addProperty("id", employee.getId());
            employeeObject.addProperty("firstName", employee.getFirstName());
            employeeObject.addProperty("lastName", employee.getLastName());
            employeeObject.addProperty("photo", employee.getPhoto());
            employeesArray.add(employeeObject);
        }
        employeesObject.add("employee", employeesArray);
        JsonObject rootObject = new JsonObject();
        rootObject.add("employees", employeesObject);
        try (JsonWriter jw = new JsonWriter(new FileWriter(fileRoute))) {
            jw.setIndent("  ");
            new Gson().toJson(rootObject, jw);
        }
    }

    public List<String[]> parseJsonStructure(String rawJsonContent) throws InvalidJsonFileException {
        ArrayList<String[]> parsedObjects = new ArrayList<>();
        try {
            JsonElement jsonElement = JsonParser.parseString(rawJsonContent);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject employees = getJsonObjectField(jsonObject, "employees").getAsJsonObject();
            JsonArray employee = getJsonObjectField(employees, "employee").getAsJsonArray();
            for (JsonElement employeeElement : employee) {
                JsonObject employeeObject = employeeElement.getAsJsonObject();
                String id = getJsonObjectField(employeeObject, "id").getAsString();
                String firstName = getJsonObjectField(employeeObject, "firstName").getAsString();
                String lastName = getJsonObjectField(employeeObject, "lastName").getAsString();
                String photo = getJsonObjectField(employeeObject, "photo").getAsString();
                parsedObjects.add(new String[]{id, firstName, lastName, photo});
            }
        } catch (JsonSyntaxException e) {
            throw new InvalidJsonFileException(e.getMessage());
        }
        return parsedObjects;
    }

    private JsonElement getJsonObjectField(JsonObject parentObject, String fieldName) throws InvalidJsonFileException {
        if (!parentObject.has(fieldName)) {
            throw new InvalidJsonFileException("Field " + fieldName + " not found");
        }
        return parentObject.get(fieldName);
    }
}