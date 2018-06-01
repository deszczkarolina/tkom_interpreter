import tkom.parser.Parser;
import tkom.parser.Program;
import tkom.scanner.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Application {

    public static void main(String[] argv) {

        Parser parser;
        Scanner scanner;
        Program program;

        if (argv.length != 1) {
            System.out.println("invalid number of arguments, one argument expected");
            return;
        }

        String fileName = argv[0];
        File file = new File(fileName);

        try {
            InputStream in = new FileInputStream(file);
            scanner = new Scanner(in);
            parser = new Parser(scanner);
            program = parser.parse();
            program.execute();
            System.out.println(program.getValue());
        } catch (IOException e) {
            System.out.println("file " + fileName + "doesn't exist");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
