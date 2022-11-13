package com.project.backend;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeFile {
    private String fileContent;

    public String getFileContent() {
        if (fileContent == null) {
            throw new RuntimeException("File content is null, please read the file first");
        }
        return fileContent;
    }

    public void readFile(String fileRoute) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileRoute))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        }
        this.fileContent = fileContent.toString();
    }

    public boolean validateJSONStructure() {
        try {
            JsonElement jsonElement = JsonParser.parseString(getFileContent());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject employees = getJsonObjectField(jsonObject, "employees").getAsJsonObject();
            JsonArray employee = getJsonObjectField(employees, "employee").getAsJsonArray();
            for (JsonElement employeeElement : employee) {
                JsonObject employeeObject = employeeElement.getAsJsonObject();
                String id = getJsonObjectField(employeeObject, "id").getAsString();
                String firstName = getJsonObjectField(employeeObject, "firstName").getAsString();
                String lastName = getJsonObjectField(employeeObject, "lastName").getAsString();
                String photo = getJsonObjectField(employeeObject, "photo").getAsString();
            }
            return true;
        } catch (InvalidJsonFieldException | JsonSyntaxException e) {
            return false;
        }
    }

    private JsonElement getJsonObjectField(JsonObject parentObject, String fieldName) throws InvalidJsonFieldException {
        if (!parentObject.has(fieldName)) {
            throw new InvalidJsonFieldException("Field " + fieldName + " not found");
        }
        return parentObject.get(fieldName);
    }
}
