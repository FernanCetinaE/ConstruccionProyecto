package com.project.frontend;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

import com.project.backend.Employee;
import com.project.backend.EmployeeFile;
import com.project.backend.EmployeeManager;
import com.project.backend.InvalidJsonFileException;

public class ContentDisplay {
    private List<String> employeeList;
    private String[] headers = {"Identificador: ", "Nombre: ", "Apellido: ", "Imagen: "};
    private List<JTextField> textFields;
    private int employeeIndex = 0;
    private JLabel currentImg;

    public ContentDisplay(){
        employeeList = new ArrayList<String>();
        fetchEmployees();
        currentImg = new JLabel();
        updateImgUrl();
    }

    public void renderDisplay(){
        JFrame frame = new JFrame("Contabilidad");  
        JPanel panel = new JPanel();

        textFields = renderEmployeeData(0);
        for (int i = 0; i < textFields.size(); i++) {
            panel.add(new JLabel(headers[i]));
            panel.add(textFields.get(i));
        }

        JButton previo = new JButton();   
        previo.setText("☜Previo");
        previo.addActionListener(
            event->{
                if(employeeIndex != 0) employeeIndex--;
                updateTextField(textFields, employeeIndex);
                updateImgUrl();
            }
        ); 

        JButton siguiente = new JButton();   
        siguiente.setText("Siguiente☞");
        siguiente.addActionListener(
            event ->{
                if(employeeIndex != employeeList.size()-1) employeeIndex++;
                updateTextField(textFields, employeeIndex);
                updateImgUrl();
            }
        ); 

        JButton actualizar = new JButton();   
        actualizar.setText("Guadar");
        actualizar.addActionListener(
            event ->{
                updateEmployee();
            }
        ); 

        JButton eliminar = new JButton();   
        eliminar.setText("borrar");
        eliminar.addActionListener(
            event ->{
                deleteEmployee();
            }
        ); 

        panel.add(previo); 
        panel.add(siguiente);
        panel.add(actualizar);
        panel.add(eliminar);
        panel.add(currentImg);
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

    private List<JTextField> renderEmployeeData(int index){
        String[] employeeData = employeeList.get(index).split(",");
        ArrayList<JTextField> listOfFields = new ArrayList<>();

        for (int i = 0; i < employeeData.length; i++) {
            listOfFields.add(renderTextField(employeeData[i],i!=0));
        }

        return listOfFields;
    }

    private void updateTextField(List<JTextField> textField, int index){
        String[] employeeData = employeeList.get(index).split(",");

        for (int i = 0; i < textField.size(); i++) {
            textField.get(i).setText(employeeData[i]);
        }
    }

    private void updateImgUrl(){
        String urlString = employeeList.get(employeeIndex).split(",")[3];
        Image image = null;
        try{
            URL url = new URL(urlString);
            image = ImageIO.read(url);
        } catch (IOException e){
            e.printStackTrace();
        }
        currentImg.setIcon(new ImageIcon(image));
    }

    /* CRUD DE EMPLOYEES */
    private void fetchEmployees(){
        EmployeeFile repository = new EmployeeFile("employees.json");
        EmployeeManager employeeManager = new EmployeeManager(repository);

        try {
            employeeManager.loadEmployeesFromJson();
            employeeList = employeeManager.getEmployeesAsString();
        } catch (IOException | InvalidJsonFileException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(){
        EmployeeFile repository = new EmployeeFile("employees.json");
        EmployeeManager employeeManager = new EmployeeManager(repository);

        try{
            employeeManager.loadEmployeesFromJson();
            Employee employee = new Employee(textFields.get(0).getText(), textFields.get(1).getText(), 
                                            textFields.get(2).getText(), textFields.get(3).getText());
                                            
            employeeManager.modifyEmployeeById(textFields.get(0).getText(),employee);
        } catch (IOException | InvalidJsonFileException e){
            e.printStackTrace();
        }

        fetchEmployees();
        updateImgUrl();
    }

    //eliminar empleado
    private void deleteEmployee(){
        EmployeeFile repository = new EmployeeFile("employees.json");
        EmployeeManager employeeManager = new EmployeeManager(repository);

        try{
            employeeManager.loadEmployeesFromJson();
            employeeManager.deleteEmployeeById(textFields.get(0).getText());
        } catch (IOException | InvalidJsonFileException e){
            e.printStackTrace();
        }

        fetchEmployees();
        if (employeeIndex!=0) employeeIndex = employeeIndex -1;
        updateTextField(textFields, employeeIndex);
        updateImgUrl();
    }
}
