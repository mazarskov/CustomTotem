import java.io.*;

public class App {
    public static void main(String[] args) {
        try {
            String resourcePath = System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\resourcepacks";
            new File(resourcePath + "\\My_pack\\assets\\minecraft\\textures\\item").mkdirs();
            File myFile = new File(resourcePath + "\\My_pack", "pack.mcmeta");
            myFile.createNewFile();

            String content = "{\n  \"pack\": {\n    \"pack_format\": 15,\n    \"description\": \"Skibidi toilet\"\n  }\n}";
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
