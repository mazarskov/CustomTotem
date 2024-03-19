import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.Font.*;
import javax.swing.*;
public class App {
    public static void main(String[] args) {
       
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        JFrame f = new JFrame();//creating instance of JFrame  
        JLabel label = new JLabel("hello!");
        
        label.setBounds(15,15,300,100);
        JButton b = new JButton("Create pack!");//creating instance of JButton 
        b.setBounds(125,100,150, 40);//x axis, y axis, width, height  
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWindows) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","png");
                    fileChooser.setFileFilter(filter);
                    int result = fileChooser.showOpenDialog(f);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String extension = getFileExtension(selectedFile);
                        if (extension != null && extension.equals("png")) {
                            // Process the selected PNG file
                            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                            String path = selectedFile.getAbsolutePath();
                            File selectedFile2 = new File(path);
                            String destinationFolder = "C:\\Users\\Max\\AppData\\Roaming\\.minecraft\\resourcepacks";
                            copyImage(selectedFile2, destinationFolder);
                        } else {
                            // Show a message to the user indicating only PNG files are allowed
                            JOptionPane.showMessageDialog(f, "Only PNG files are allowed.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        macFileCreator();
                    }
                }
            }
        });
        
        
        
        
        
        
        
        f.add(b);//adding button in JFrame   
        f.add(label); 
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
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    private static void copyImage(File selectedFile, String destinationFolderPath) {
        try {
            // Create Path objects for the source file and the destination folder
            Path sourceFilePath = selectedFile.toPath();
            Path destinationFolderPathObj = Paths.get(destinationFolderPath);
            
            // Assign a static filename for the copied file
            String fileName = "static_image.png";

            // Create a Path object for the destination file
            Path destinationFilePath = destinationFolderPathObj.resolve(fileName);

            // Copy the file to the destination folder
            Files.copy(sourceFilePath, destinationFilePath);

            System.out.println("File copied successfully to: " + destinationFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
