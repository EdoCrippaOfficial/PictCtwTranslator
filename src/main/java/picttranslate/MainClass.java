package picttranslate;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import picttranslate.parser.PictCustomVisitor;
import picttranslate.parser.PictLexer;
import picttranslate.parser.PictParser;

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
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    String ls = System.getProperty("line.separator");
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(ls);
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    reader.close();
                    String inputText = stringBuilder.toString();
                    inputText = inputText.replace("[", " ")
                            .replace("]", " ")
                            .replace(".", "")
                            .replace("|", ", ")
                            .replace("_", "-");

                    CharStream input = CharStreams.fromString(inputText);
                    PictLexer lexer = new PictLexer(input);
                    PictParser parser = new PictParser(new CommonTokenStream(lexer));
                    PictCustomVisitor visitor = new PictCustomVisitor();
                    String s = visitor.visit(parser.start()).toString();
                    s = "Model " + fileName + "\n\n" + s;

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

//throw new ParseCancellationException("Error parsing the text");




