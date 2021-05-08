import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;

/**
 * Created by worm2fed on 03.02.17.
 */
public class Main {
    public static void main(String[] args) {
        BitSet bitset = new BitSet();
        String line = null;
        String result = "";
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;

        try {
            bf_reader = new BufferedReader(new FileReader("input.txt"));

            int ctr = 0;
            while ((line = bf_reader.readLine()) != null) {
                String[] cur_line = line.split(" ");

                switch (ctr) {
                    case 0:
                        for (int i = 0; i < cur_line.length; i++) {
                            try {
                                bitset.add(Integer.parseInt(cur_line[i]));
                            } catch (NumberFormatException e){
                                System.out.println("not_a_num");
                            }
                        }

                        break;
                    case 1:
                        try {
                            bf_writer = new BufferedWriter(new FileWriter("output.txt"));

                            for (int i = 0; i < cur_line.length; i++) {
                                try {
                                    bf_writer.write(
                                            Boolean.toString(
                                                    bitset.exist(
                                                            Integer.parseInt(cur_line[i]))));
                                    bf_writer.write(" ");
                                } catch (NumberFormatException e){
                                    System.out.println("not_a_num");
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("write_err");
                        } finally {
                            if (!closeFile(bf_writer))
                                System.out.println("close_err");
                        }

                        break;
                }
                ctr++;
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
