package com.project.backend;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFile {
    private String rawContent;

    public String getRawContent() {
        if (rawContent == null) {
            throw new RuntimeException("File content is null, please load the file first");
        }
        return rawContent;
    }

    public void loadFile(String fileRoute) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileRoute))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        }
        this.rawContent = fileContent.toString();
    }

    public List<String[]> parseJsonStructure() throws InvalidJsonFileException {
        ArrayList<String[]> parsedObjects = new ArrayList<>();
        try {
            JsonElement jsonElement = JsonParser.parseString(getRawContent());
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