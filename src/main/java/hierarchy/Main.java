package hierarchy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        final RequestProcessor processor = new RequestProcessorImpl();
        String line;
        try (Scanner stdin = new Scanner(System.in)) {
            while (stdin.hasNextLine() && !(line = stdin.nextLine()).equals("")) {
                processor.processRequest(line);
            }
        }
    }
}
