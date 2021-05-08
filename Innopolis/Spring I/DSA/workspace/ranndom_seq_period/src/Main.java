import java.io.*;
import java.util.Comparator;

import static java.lang.Math.abs;

/**
 * Created by worm2fed on 22.03.17.
 */
public class Main {
    public static void main(String[] args) {
        String line = null;
        String result = null;
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;
        RBTree tree = new RBTree();
        MyRandom random = null;

        try {
            bf_reader = new BufferedReader(new FileReader("input.txt"));
            // Create random
            try {
                random = new MyRandom(Integer.parseInt(bf_reader.readLine()));
            } catch (NumberFormatException e) {
                System.out.println("format_err");
            }

            for (int i = 0; ; i++) {
                Item item = new Item(tree.getSize() - 1, random.nextDouble());
                Item current = (Item) tree.find(item);

                if (current == null)
                    tree.insert(item);
                else {
                    result = Integer.toString(i - current.getIndex());
                    break;
                }
            }

            try {
                bf_writer = new BufferedWriter(new FileWriter("output.txt"));
                bf_writer.write(result);
            } catch (IOException e) {
                System.out.println("write_err");
            } finally {
                if (!closeFile(bf_writer))
                    System.out.println("close_err");
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
