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
import java.awt.image.BufferedImage;

import java.awt.Font.*;

import javax.imageio.ImageIO;
import javax.swing.*;
public class App {
    public static String absolutePath = "";
    public static void main(String[] args) {
        
        
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        if (isWindows) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JFrame f = new JFrame("CustomTotem ALPHA");
        
        JLabel label = new JLabel("Select a .png image and then then the 'Create Pack' button will appear.");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setBounds(40,25,800,25);

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(150,55,200,200);
        
        JButton createPackButton = new JButton("Create pack!");
        createPackButton.setBounds(475,300,200, 30);
        createPackButton.setVisible(false);

        JButton chooseImageButton = new JButton("Choose image.");
        chooseImageButton.setBounds(100,300,200, 30);
        
        chooseImageButton.addActionListener(new ActionListener() {
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
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                        label.setFont(new Font("Arial", Font.PLAIN, 20));
                        label.setText("Selected file: " + selectedFile.getAbsolutePath());
                        absolutePath = selectedFile.getAbsolutePath();
                        chooseImageButton.setText("Choose another image");
                        createPackButton.setVisible(true);
                        try {
                            BufferedImage image = ImageIO.read(new File(absolutePath));
                            BufferedImage resizedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g = resizedImage.createGraphics();
                            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            g.drawImage(image, 0, 0, 200, 200, null);
                            g.dispose();
                            imageLabel.setIcon(new ImageIcon(resizedImage));
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(f, "Only PNG files are allowed.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        createPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (absolutePath.equals("")) {
                    JOptionPane.showMessageDialog(f, "No Image file selected", "Invalid File", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (isWindows) {
                        packCreator(windowsFileCreator());
                    } else {
                        packCreator(macFileCreator());
                    }
                    JOptionPane.showMessageDialog(f, "Resource Pack with a custom totem created! Press OK to exit", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        });
        
        f.add(label);
        f.add(imageLabel);
        f.add(chooseImageButton);
        f.add(createPackButton);
        f.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        f.validate();
        f.repaint();
        f.setSize(800,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static String windowsFileCreator() {
        try {
            String resourcePath = System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\resourcepacks";
            int suffix = 0;
            String packFolderName = "CustomTotem";
            String packFolderPath = resourcePath + "\\" + packFolderName;
            while (new File(packFolderPath).exists()) {
                suffix++;
                packFolderName += "(" + suffix + ")";
                packFolderPath = resourcePath + "\\" + packFolderName;
            }
            new File(packFolderPath + "\\assets\\minecraft\\textures\\item").mkdirs();
            File myFile = new File(packFolderPath, "pack.mcmeta");
            myFile.createNewFile();
            System.out.println("Directory and mcmeta file created");
            String content = "{\n  \"pack\": {\n    \"pack_format\": 15,\n    \"description\": \"CustomTotem pack\"\n  }\n}";
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println("Mcmeta file filled with info");
            return packFolderPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String macFileCreator() {
        try {
            String resourcePath = System.getProperty("user.home") + "/Library/Application Support/minecraft/resourcepacks";
            int suffix = 0;
            String packFolderName = "CustomTotem";
            String packFolderPath = resourcePath + "/" + packFolderName;
            while (new File(packFolderPath).exists()) {
                suffix++;
                packFolderName += "(" + suffix + ")";
                packFolderPath = resourcePath + "/" + packFolderName;
            }
            new File(packFolderPath + "/assets/minecraft/textures/item").mkdirs();
            File myFile = new File(packFolderPath, "pack.mcmeta");
            myFile.createNewFile();
            System.out.println("Directory and mcmeta file created");
            String content = "{\n  \"pack\": {\n    \"pack_format\": 15,\n    \"description\": \"CustomTotem pack\"\n  }\n}";
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println("Mcmeta file filled with info");
            return packFolderPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            Path sourceFilePath = selectedFile.toPath();
            Path destinationFolderPathObj = Paths.get(destinationFolderPath);
            String fileName = "totem_of_undying.png";
            Path destinationFilePath = destinationFolderPathObj.resolve(fileName);
            Files.copy(sourceFilePath, destinationFilePath);
            System.out.println("File copied successfully to: " + destinationFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void copyPackImage(File selectedFile, String destinationFolderPath) {
        try {
            Path sourceFilePath = selectedFile.toPath();
            Path destinationFolderPathObj = Paths.get(destinationFolderPath);
            String fileName = "pack.png";
            Path destinationFilePath = destinationFolderPathObj.resolve(fileName);
            Files.copy(sourceFilePath, destinationFilePath);
            System.out.println("File copied successfully to: " + destinationFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void packCreator(String folder) {
        String path = absolutePath;
        File selectedFile2 = new File(path);
        String packFolder = folder;
        String itemTexturePath = packFolder + "\\assets\\minecraft\\textures\\item";
        copyPackImage(selectedFile2, packFolder);
        copyImage(selectedFile2, itemTexturePath);
    }
}
