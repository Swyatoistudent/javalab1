package Parser;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code her


        Scanner scanner = new Scanner(System.in);

        System.out.println("Input file : ");
        String inputFilePath = scanner.next();

        File newFile = new File(inputFilePath);
        if (!newFile.exists()){
            throw new FileNotFoundException(newFile.getName());
        }
        System.out.println("Output file : ");
        String outputFilePath = scanner.next();

        System.out.println("Input delimiter: ");
        String delimiter = scanner.next();

        System.out.println("mode len/parse: ");
        String mode = scanner.next();

        Parser a = new Parser(inputFilePath, outputFilePath);
        a.parseFile(delimiter.charAt(0),mode);
    }
}


