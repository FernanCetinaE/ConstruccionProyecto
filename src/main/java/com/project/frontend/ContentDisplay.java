package com.project.frontend;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;

public class ContentDisplay {
    private List<String> employeeList;
    private String[] headers = {"Identificador: ", "Nombre: ", "Apellido: ", "Imagen: "};
    private List<JTextField> textFields;
    private int employeeIndex = 0;
    private JLabel currentImg;

    public ContentDisplay(){
        employeeList = new ArrayList<String>();
        employeeList.add("1,Jeff,Bezos,https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_93nQOc7LMrdcjPK4LhDcdyThGbgBGvxToNPdT5GHHY21raN49dv8a5w&s=10");
        employeeList.add("2,Elon,Musk,https://i1.sndcdn.com/artworks-uJOYShilCZeAYJJE-sBmXcA-t500x500.jpg");
        employeeList.add("3,Mark,Zuckerberg,https://dircomfidencial.com/wp-content/uploads/2021/10/mark.jpg");
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
        previo.setText("Previo");
        previo.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(employeeIndex != 0) employeeIndex--;
                    updateTextField(textFields, employeeIndex);
                    updateImgUrl();
                }
            }
        ); 

        JButton siguiente = new JButton();   
        siguiente.setText("Siguiente");
        siguiente.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(employeeIndex != employeeList.size()-1) employeeIndex++;
                    updateTextField(textFields, employeeIndex);
                    updateImgUrl();
                }
            }
        ); 

        frame.getContentPane().add(currentImg, BorderLayout.CENTER);
        panel.add(new JLabel(headers[headers.length-1]));//Renderiza palabra: Imagen
        panel.add(currentImg);
        panel.add(previo); 
        panel.add(siguiente);
       
        frame.add(panel);  
        frame.setSize(400, 800);  
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
