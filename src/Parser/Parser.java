package Parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Parser {
    FileReader reader;
    FileWriter writer;



    public  Parser(String input_file,String output_file) throws IOException {

        reader = new FileReader(input_file);
        writer = new FileWriter(output_file);

    }


    void parseFile(char delimeter,String mode) throws IOException {
        LinkedList<StringBuilder> linkedList = new LinkedList<>();
        int c;
        boolean active = false;
        StringBuilder buffer = new StringBuilder();
        while ((c = reader.read()) != -1) {
            if ((char)c!='\n'){
                buffer.append((char) c);
            }

            if (c == '"') {
                active = !active;
            }
            if (c == '\n') {
                linkedList.addLast(buffer.deleteCharAt(buffer.length()-1));
                buffer = new StringBuilder();
                int i = 0;
                while (i < linkedList.size()) {
                    String result=String.valueOf(linkedList.get(i));
                    if(result.charAt(0)=='"' && result.charAt(result.length()-1)=='"'){
                        result = result.substring(1,result.length()-1);
                    }
                    if(mode.equals("parse")) {
                        writer.write(result);
                    }
                    if (mode.equals("len")){
                        writer.write(String.valueOf(result.length()));
                    }
                    writer.write("+");
                    i++;
                }
                writer.write("\n");
                active = false;
                linkedList = new LinkedList<>();
            }
            if (c == delimeter && !active) {

                buffer.deleteCharAt(buffer.length() - 1);


                linkedList.addLast(buffer);


                buffer = new StringBuilder();
            }

        }
        linkedList.addLast(buffer);
        int i = 0;
        while (i < linkedList.size()) {
            String result=String.valueOf(linkedList.get(i));
            if(result.charAt(0)=='"' && result.charAt(result.length()-1)=='"'){
                result = result.substring(1,result.length()-1);
            }
            if(mode.equals("parse")) {
                writer.write(result);
            }
            if (mode.equals("len")){
                writer.write(String.valueOf(result.length()));
            }
            writer.write("+");
            i++;
        }

        writer.close();
    }

}
