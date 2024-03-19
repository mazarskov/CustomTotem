import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon.*;
import java.awt.Font.*;
import javax.swing.*;
public class App {
    public static void main(String[] args) {
       
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        JFrame f=new JFrame();//creating instance of JFrame  
        JButton b=new JButton("Create pack!");//creating instance of JButton 
        b.setBounds(125,100,150, 40);//x axis, y axis, width, height  
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWindows) {
                    windowsFileCreator();
                } else {
                    macFileCreator();
                }
            }
        });
        
        
        
        
        
        
        
        f.add(b);//adding button in JFrame    
        f.setSize(400,200);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible  
        f.setResizable(false);
    }


    public static void windowsFileCreator() {
        try {
            String resourcePath = System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\resourcepacks";
            new File(resourcePath + "\\My_pack\\assets\\minecraft\\textures\\item").mkdirs();
            File myFile = new File(resourcePath + "\\My_pack", "pack.mcmeta");
            myFile.createNewFile();
            System.out.println("Directory and mcmeta file created");
            String content = "{\n  \"pack\": {\n    \"pack_format\": 15,\n    \"description\": \"Skibidi toilet\"\n  }\n}";
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println("Mcmeta file filled with info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void macFileCreator() {
        try {
            String resourcePath = System.getProperty("user.home") + "/Library/Application Support/minecraft/resourcepacks";
            new File(resourcePath + "/My_pack/assets/minecraft/textures/item").mkdirs();
            File myFile = new File(resourcePath + "/My_pack", "pack.mcmeta");
            myFile.createNewFile();
            System.out.println("Directory and mcmeta file created");
            String content = "{\n  \"pack\": {\n    \"pack_format\": 15,\n    \"description\": \"Skibidi toilet\"\n  }\n}";
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println("Mcmeta file filled with info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
