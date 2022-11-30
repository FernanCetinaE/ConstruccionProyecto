package com.project.frontend;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.project.backend.Employee;
import com.project.backend.EmployeeFile;
import com.project.backend.EmployeeManager;
import com.project.backend.InvalidJsonFileException;

import java.util.List;
import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import java.util.ArrayList;

public class AddEmployee extends javax.swing.JFrame{
    private String[] headers = {"Nombre: ", "Apellido: ", "Imagen: "};
    private List<JTextField> textFields;
    ContentDisplay contentDisplay;

    public AddEmployee(ContentDisplay contentDisplay) {
        textFields = new ArrayList<JTextField>();
        this.contentDisplay = contentDisplay; 
    }

    public void renderDisplay(){
        JFrame frame = new JFrame("Contabilidad");  
        JPanel panel = new JPanel();

        textFields = renderEmployeeData();
        for (int i = 0; i < textFields.size()-1; i++) {
            panel.add(new JLabel(headers[i]));
            panel.add(textFields.get(i));
        }

        JButton guardar = new JButton();   
        guardar.setText("Guardar");
        guardar.addActionListener(
            event ->{
                addEmployee();
                frame.dispose();
            }
        ); 
        panel.add(guardar);
        frame.add(panel);  
        frame.setSize(400, 600);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);  
        frame.setVisible(true);  
    }

    private JTextField renderTextField(String text,Boolean editable){
        Font font = new Font("SansSerif", Font.BOLD, 20);
        JTextField textField = new JTextField(text,18);
        textField.setText(text);
        textField.setFont(font);
        textField.setEditable(editable);
        return textField;
    }

    private List<JTextField> renderEmployeeData(){
        ArrayList<JTextField> listOfFields = new ArrayList<>();

        for (int i = 0; i <= headers.length; i++) {
            listOfFields.add(renderTextField("",true));
        }

        return listOfFields;
    }

    private void fetchEmployees(){
        EmployeeFile repository = new EmployeeFile("employees.json");
        EmployeeManager employeeManager = new EmployeeManager(repository);

        try {
            employeeManager.loadEmployeesFromJson();
            contentDisplay.employeeList = employeeManager.getEmployeesAsString();
        } catch (IOException | InvalidJsonFileException e) {
            e.printStackTrace();
        }
    }

    private void addEmployee(){
        EmployeeFile repository = new EmployeeFile("employees.json");
        EmployeeManager employeeManager = new EmployeeManager(repository);

        try{
            employeeManager.loadEmployeesFromJson();
            
            Employee employee = new Employee("100", textFields.get(0).getText(), 
                                            textFields.get(1).getText(), textFields.get(2).getText());

            if(employee.getFirstName().matches("^[a-zA-Z]+$") && employee.getLastName().matches("^[a-zA-Z]+$") && 
            employee.getPhoto().matches("^(https?:\\/\\/)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$"))
                employeeManager.addNewEmployee(employee.getFirstName(),employee.getLastName(),employee.getPhoto());

        } catch (IOException | InvalidJsonFileException e){
            e.printStackTrace();
        }

        fetchEmployees();
    }
}
