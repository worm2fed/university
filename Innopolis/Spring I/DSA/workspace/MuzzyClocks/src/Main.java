import java.io.*;
import java.util.HashMap;

import static java.lang.Integer.max;

/**
 * Created by worm2fed on 26.02.17.
 */
public class Main {
        public static void main(String[] args) {
        String line = null;
        String result = "";
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;
        int[] weights = null, costs = null;
        int capacity = 0;

        // Read file
        try {
            bf_reader = new BufferedReader(new FileReader("input.txt"));

            int ctr = 0;
            while ((line = bf_reader.readLine()) != null) {
                // First line
                if (ctr == 0) {
                    String[] data = line.split(" ");
                    int size = data.length / 2;

                    weights = new int[size];
                    costs = new int[size];

                    for (int i = 0, j = 0; i < data.length; i++, j++) {
                        String[] tmp = data[i].split(":");
                        // write weight
                        weights[j] = (int) ((Double.parseDouble(tmp[0]) * 60) + Double.parseDouble(tmp[1]));
                        i++;
                        // write cost
                        costs[j] = (int) (Double.parseDouble(data[i]) * 100);
                    }

                    ctr++;
                // Second line
                } else if (ctr == 1) {
                    capacity = (int) (Double.parseDouble(line) * 100);
                    ctr++;
                } else
                    break;
            }
            
            result += bag(weights, costs, capacity);

            // Write to file
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
            e.printStackTrace();
        } finally {
            if (!closeFile(bf_reader))
                System.out.println("close_err");
        }
    }

    // Count max
    private static int bag(int[] weights, int[] costs, int capacity) {
        boolean[] knapTime = new boolean [100000];
        int[] knapPrice = new int [100000];

        knapTime[0] = true;
        for (int i = 0; i < weights.length; i++) {
            for (int j = capacity; j >= 0; j--) {
                if (knapTime[j]) {
                    knapPrice[j + costs[i]] = max(knapPrice[j] +  weights[i], knapPrice[j + costs[i]]);
                    knapTime[j + costs[i]] = true;
                }
            }
        }

        int max = 0;
        for (int i = 0; i <= capacity; i++) {
            if (knapPrice[i] > max) {
                max = knapPrice[i];
            }
        }

        return max;
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
