package com.project.frontend;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

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
        EmployeeFile repository = new EmployeeFile("employees.json");
        EmployeeManager employeeManager = new EmployeeManager(repository);

        try {
            employeeManager.loadEmployeesFromJson();
            employeeList = employeeManager.getEmployeesAsString();
        } catch (IOException | InvalidJsonFileException e) {
            e.printStackTrace();
        }
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

        panel.add(previo); 
        panel.add(siguiente);
        panel.add(currentImg);
        
        frame.add(panel);  
        frame.setSize(400, 600);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);  
        frame.setVisible(true);  
    }

    private JTextField renderTextField(String text){
        Font font = new Font("SansSerif", Font.BOLD, 20);
        JTextField textField = new JTextField(text,18);
        textField.setText(text);
        textField.setFont(font);
        textField.setEditable(false);
        return textField;
    }

    private List<JTextField> renderEmployeeData(int index){
        String[] employeeData = employeeList.get(index).split(",");
        ArrayList<JTextField> listOfFields = new ArrayList<>();

        for (int i = 0; i < employeeData.length-1; i++) {
            listOfFields.add(renderTextField(employeeData[i]));
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
}
