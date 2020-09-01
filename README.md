# PictCtwTranslator
Translate files from Pict to CTWedge

usage: java -jar PictTranslate.jar "path to folder"

### Usage as a Library
```
File file;
String fileName = "model name";
PictTranslator translator = new PictTranslator();
String output = translator.translate(file, fileName);
```
OR
```
String text;
String fileName = "model name";
PictTranslator translator = new PictTranslator();
String output = translator.translate(text, fileName);
```
