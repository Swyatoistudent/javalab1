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
        int first_comm_char = -1;
        int end_comm_char = -1;
        boolean comment = false;
        boolean active = false;
        boolean del = false;
        int first = -1;
        int second = -1;
        int j = 0;
        StringBuilder buffer = new StringBuilder();
        StringBuilder buffer_comm = new StringBuilder();
        while ((c = reader.read()) != -1) {
            j++;

            if((char)c == '/'||(char)c=='*'){
                buffer_comm.append((char)c);
            }
            if(buffer_comm.length()>2){
                buffer_comm = new StringBuilder();
            }
            if(buffer_comm.toString().equals("/*")){
                buffer.deleteCharAt(j-2);
                comment=true;
                buffer_comm = new StringBuilder();

            }

            if ((char)c!='\n'&& !comment){
                buffer.append((char) c);
            }
            if(buffer_comm.toString().equals("*/")){
                j=0;

                comment=false;
                buffer_comm = new StringBuilder();
            }
            if(first==1 && second!=-1 &&buffer.length()!=0&& !del){
                buffer.deleteCharAt(0);
                buffer.deleteCharAt(second-2);
                del= true;
            }
            if (c == '"' ) {
                if(first==-1) {
                    first = j;
                }
                else if(second==-1){
                    second = j;
                }
                active = !active;
            }
            if (c == '\n'&&!comment) {

                linkedList.addLast(buffer.deleteCharAt(buffer.length()-1));
                buffer = new StringBuilder();
                int i = 0;
                while (i < linkedList.size()) {
                    String result=String.valueOf(linkedList.get(i));

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
                j=0;
                first = -1;
                second = -1;
                del = false;
            }
            if (c == delimeter && !active&& !comment) {
                j=0;
                first = -1;
                second = -1;
                del =false;
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
