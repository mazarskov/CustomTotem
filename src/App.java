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
    public static String absolutePath = "";
    public static void main(String[] args) {
        //FlowLayout experimentLayout = new FlowLayout();
        GridLayout layout = new GridLayout();
        //experimentLayout.setAlignment(FlowLayout.LEADING);
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        //JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));

        JFrame f = new JFrame("CustomTotem ALPHA");//creating instance of JFrame  
        JLabel label = new JLabel("hello!");
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        label.setBounds(40,200,600,100);
        JButton b = new JButton("Create pack!");//creating instance of JButton 
        JButton c = new JButton("Choose image.");//creating instance of JButton 
        JButton d = new JButton("Print path to image.");//creating instance of JButton 
        //JPanel panel = new JPanel(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();
        //gbc.weighty = 1;
        //gbc.weightx = 1;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.gridwidth = GridBagConstraints.REMAINDER;
        //gbc.anchor = GridBagConstraints.SOUTH;
        b.setBounds(125,300,150, 30);//x axis, y axis, width, height  
        c.setBounds(300,300,150, 30);//x axis, y axis, width, height  
        d.setBounds(475,300,150, 30);
        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(absolutePath);
            }
        });
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        label.setFont(new Font("Arial", Font.PLAIN, 20));
                        label.setText("Selected file: " + selectedFile.getAbsolutePath());
                        absolutePath = selectedFile.getAbsolutePath();
                    } else {
                        // Show a message to the user indicating only PNG files are allowed
                        JOptionPane.showMessageDialog(f, "Only PNG files are allowed.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWindows) {
                            String path = absolutePath;
                            if (absolutePath.equals("")) {
                                JOptionPane.showMessageDialog(f, "No Image file selected", "Invalid File", JOptionPane.ERROR_MESSAGE);
                            } else {
                                File selectedFile2 = new File(path);
                                String destinationFolder = windowsFileCreator();
                                copyImage(selectedFile2, destinationFolder);
                                JOptionPane.showMessageDialog(f, "Resource Pack with a custom totem created!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                                System.exit(0);
                            }

                    } else {
                        macFileCreator();
                    }
                }
            });
        
        
        //panel.add(label); 
        //panel.add(c);
        //panel.add(b);//adding button in JFrame   
        //f.add(c);
        f.add(label);
        f.add(c);
        f.add(b);
        f.add(d);
        
        
        //f.getContentPane().add(panel);
        f.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        f.validate();
        f.repaint();
        f.setSize(800,400);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible  
        f.setResizable(false);
    }


    public static String windowsFileCreator() {
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
            return resourcePath + "\\My_pack\\assets\\minecraft\\textures\\item";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
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
            String fileName = "totem_of_undying.png";

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
