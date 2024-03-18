import java.io.*;

public class App {
    public static void main(String[] args) {
       
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        try {
            if (isWindows) {
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
            } else {
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
            }
            
            
            


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
