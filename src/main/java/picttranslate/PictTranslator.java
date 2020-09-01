package picttranslate;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import picttranslate.parser.PictCustomVisitor;
import picttranslate.parser.PictLexer;
import picttranslate.parser.PictParser;

import java.io.*;
import java.util.Random;

public class PictTranslator {

    private Random random = new Random();

    public String translate(String s, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(s));
        return translateInternal(reader, fileName);
    }

    public String translate(File f, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        return translateInternal(reader, fileName);
    }

    private String translateInternal(BufferedReader reader, String fileName) throws IOException {
        if (fileName == null)
            fileName = "m" + random.nextInt();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        String ls = System.getProperty("line.separator");
        boolean parameters = true;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("if") || line.startsWith("IF") || line.startsWith("{"))
                parameters = false;
            if (parameters && !line.contains(":"))
                line = line.replaceFirst(",", ":");
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();
        String inputText = stringBuilder.toString();
        inputText = inputText.replace("[", " ")
                .replace("]", " ")
                .replace(".", "")
                .replace("~", "")
                .replace("|", ", ")
                .replace("_", "")
                .replace("-", "");

        CharStream input = CharStreams.fromString(inputText);
        PictLexer lexer = new PictLexer(input);
        PictParser parser = new PictParser(new CommonTokenStream(lexer));
        PictCustomVisitor visitor = new PictCustomVisitor();
        String s = visitor.visit(parser.start()).toString();
        s = "Model " + fileName + "\n\n" + s;
        return s;
    }

}
