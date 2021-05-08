import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by worm2fed on 27.02.17.
 */
public class Main {
    static double[] tmp_array;

    public static void main(String[] args) {
        String line = null;
        String result = "";
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;
        List< List<Double> > data = new ArrayList<>();
        int data_i = 0;

        // Read file
        try {
            bf_reader = new BufferedReader(new FileReader("input.csv"));
            // Mark with buffer size
            bf_reader.mark(1000);
            // Get column number
            int column_num = countMatches(bf_reader.readLine(), ",") + 1;
            // Reset buffer
            bf_reader.reset();

            // Create data structure
            for (int i = 0; i < column_num; i++)
                data.add(new ArrayList<Double>());

            while ((line = bf_reader.readLine()) != null) {
                String[] row = line.split(",");

                // Fill `data` with data
                try {
                    for (int i = 0; i < column_num; i++)
                        data.get(i).add(Double.parseDouble(row[i]));
                } catch (NumberFormatException e) {
                    System.out.println("wrong_number_format");
                }
            }

            // Sort columns
            for (; data_i < column_num; data_i++) {
                double[] tmp = sort(data.get(data_i).toArray());

                if (checkStep(tmp)) {
                    result += data_i;
                    break;
                }
            }

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

    // Count char matches in string
    private static int countMatches(String str, String sub) {
        if(!str.isEmpty() && !sub.isEmpty()) {
            int count = 0;

            for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    // Check step
    private static boolean checkStep(double[] tmp) {
        if (tmp.length >= 2) {
            double step = tmp[1] - tmp[0];

            for (int i = 1; i < tmp.length; i++) {
                if (Math.abs(step - (tmp[i] - tmp[i - 1])) > 1e-4 || step == 0.0)
                    return false;

                step = tmp[i] - tmp[i - 1];
            }

            return true;
        } else
            return true;
    }

    // Merge Sort implementation
    private static double[] sort(Object[] array) {
        tmp_array = new double[array.length];

        for (int i = 0; i < tmp_array.length; i++)
            tmp_array[i] = (double) array[i];

        return internal_sort(0, tmp_array.length);
    }

    // Part of sort
    private static double[] internal_sort(int start, int end) {
        if (end - start <= 1)
            return Arrays.copyOfRange(tmp_array, start, end);

        int median = (start + end) / 2;
        double[] left = internal_sort(start, median);
        double[] right = internal_sort(median, end);

        return merge(left, right);
    }

    // Merge rwo parts of array
    private static double[] merge(double[] left, double[] right) {
        double[] tmp = new double[left.length + right.length];

        for (int i = 0, l_i = 0, r_i = 0; i < tmp.length; i++) {
            if (r_i < right.length) {
                if (l_i < left.length && left[l_i] <= right[r_i]) {
                    tmp[i] = left[l_i];
                    l_i++;
                } else {
                    tmp[i] = right[r_i];
                    r_i++;
                }
            } else {
                tmp[i] = left[l_i];
                l_i++;
            }
        }

        return tmp;
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
