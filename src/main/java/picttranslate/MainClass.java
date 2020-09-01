package picttranslate;

import java.io.*;

public class MainClass {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("usage: java -jar PictTranslate.jar \"path to folder\"");
            return;
        }
        String path = args[0];
        File inputFolder = new File(path);
        File outputFolder = new File(path + "/output");
        outputFolder.mkdir();
        for (File f : inputFolder.listFiles()) {
            if (f.isFile() && f.getName().contains("txt")){
                String[] filePath = f.getAbsolutePath().split("/");
                String fileNameWithExt = filePath[filePath.length - 1];
                String fileName = fileNameWithExt.substring(0, fileNameWithExt.length() - 4);
                System.out.println("---- " + fileName);
                try{
                    PictTranslator translator = new PictTranslator();
                    String s = translator.translate(f, fileName);

                    String outputName =  outputFolder.getAbsolutePath() + "/" + fileName + ".ctw";
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
                    writer.write(s);
                    writer.close();

                    System.out.println(fileName + " translated successfully!");

                }catch (Exception e){
                    System.out.println(fileName + " not translated");
                }
            }
        }
    }

}





