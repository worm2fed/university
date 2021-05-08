import java.io.*;

/**
 * Created by worm2fed on 03.02.17.
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;
        String line;
        ShuntingYard rpn = new ShuntingYard();

        try {
            bf_reader = new BufferedReader(new FileReader("input.txt"));

            while ((line = bf_reader.readLine()) != null) {
                try {
                    bf_writer = new BufferedWriter(new FileWriter("output.txt"));
                    // Create RPN
                    rpn.create(line);
                    // Calculate RPN;
                    bf_writer.write(rpn.calculate());
                } catch (IOException e) {
                    System.out.println("write_err");
                } finally {
                    if (!closeFile(bf_writer))
                        System.out.println("close_err");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("404_err");
        } catch (IOException e) {
            System.out.println("read_err");
        } finally {
            if (!closeFile(bf_reader))
                System.out.println("close_err");
        }
    }

    // Close write thread
    private static boolean closeFile(BufferedWriter bf_writer) {
        try {
            if (bf_writer != null)
                bf_writer.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    // Close read thread
    private static boolean closeFile(BufferedReader bf_reader) {
        try {
            if (bf_reader != null)
                bf_reader.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
