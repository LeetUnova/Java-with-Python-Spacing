package src.leetu.pythonjava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        if (args.length < 2) {
            System.out.println("Must specify [source file] and [destination file] in that order.");
            return;
        }

        save(transform(args[0]), args[1]);
    }

    private static String transform(final String filename) {
        try (final Scanner scanner = new Scanner(new File(filename))) {
            String transformed = "";
            String first = scanner.nextLine();
            int lengthA = indent(first);
            int opens = 0;

            while (scanner.hasNextLine()) {
                final String second = scanner.nextLine();

                if (second.isBlank())
                    continue;
                    
                int lengthB = indent(second);

                int value = lengthA - lengthB;
                transformed += switch (value == 0 ? 0 : value > 0 ? 1 : -1) {
                    case -1 -> {
                        opens++;
                        yield first + "\n{";
                    }
                    case 1 -> {
                        opens--;
                        yield first + "\n}";
                    }
                    default -> first;
                } + "\n";

                first = second;
                lengthA = lengthB;
            }

            transformed += first + "\n";
            while (opens-- > 0)
                transformed += "}\n";

            return transformed;
        }
        catch (final FileNotFoundException exception) {
            return null;
        }
    }

    private static int indent(final String line) {
        if (line.isBlank())
            return -1;

        int i;
        for (i = 0; i < line.length() && Character.isWhitespace(line.charAt(i)); i++);

        return i;
    }

    private static void save(final String data, final String filename) {
        final File file = new File(filename);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("idk lol");
                return;
            }
        try (final PrintStream printStream = new PrintStream(file)) {
            printStream.print(data);
        }
        catch (FileNotFoundException exception) {
            System.out.println("really don't know how you did this");
        }
    }
}