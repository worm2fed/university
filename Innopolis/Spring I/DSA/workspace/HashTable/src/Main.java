import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by worm2fed on 20.02.17.
 */
public class Main {
    public static void main(String[] args) {
        YourMap<String, Integer> map = new YourMap<>();
        ArrayList<String> keys = new ArrayList<>();
        String line = null;
        String result = "";
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;
        int max_value = 0;
        String max_key = null;

        try {
            bf_reader = new BufferedReader(new FileReader("input.txt"));

            // First cycle
            while ((line = bf_reader.readLine()) != null) {
                // Convert line to lower case
                line = line.toLowerCase();
                // Delete excess symbols
                line = line.replaceAll("(\\.)|(\\,)|(\\!)|(\\?)|(\\;)|(\\:)", " ");
                line = line.replaceAll("'", " '");
                // Delete all excess whitespaces
                line = line.replaceAll("(^\\s)", "");
                line = line.replaceAll("([\\s]{2,})", " ");

                // Split line with whitespaces
                String[] word = line.split(" ");

                // Add words to map and count
                for (int i = 0; i < word.length; i++) {
                    if (map.get(word[i]) == null) {
                        keys.add(word[i]);
                        map.put(word[i], 1);
                    } else
                        map.put(word[i], map.get(word[i]) + 1);
                }
            }

            // Find max
            for (int i = 0; i < keys.size(); i++) {
                int value = map.get(keys.get(i));

                if (value > max_value) {
                    max_value = value;
                    max_key = keys.get(i);
                }
            }

            if (!map.isEmpty())
                result += max_key + " " + map.get(max_key);

            // Clear map
            map.clear();
            // Clear keys
            keys.clear();
            max_value = 0;
            max_key = null;
            bf_reader = new BufferedReader(new FileReader("input.txt"));

            // Second cycle
            while ((line = bf_reader.readLine()) != null) {
                // Convert line to lower case
                line = line.toLowerCase();
                // Delete all dots and commas
                line = line.replaceAll("(\\.)|(\\,)|(\\!)|(\\?)|(\\;)|(\\:)", " ");
                // Replace all special words
                line = line.replaceAll("(\\s|^|\\b)(a|the|in|at|to|on|not|for|'s|'d|'re|is|are|am|has|i|we|you)(\\b|$) | !(a-)", " ");
                // Delete all excess whitespaces
                line = line.replaceAll("(^\\s)", "");
                line = line.replaceAll("([\\s]{2,})", " ");

                if (line.matches("^\\s*$"))
                    continue;

                // Split line with whitespaces
                String[] word = line.split(" ");

                // Add words to map and count
                for (int i = 0; i < word.length; i++) {
                    if (map.get(word[i]) == null) {
                        keys.add(word[i]);
                        map.put(word[i], 1);
                    } else
                        map.put(word[i], map.get(word[i]) + 1);
                }
            }

            // Find max
            for (int i = 0; i < keys.size(); i++) {
                int value = map.get(keys.get(i));

                if (value > max_value) {
                    max_value = value;
                    max_key = keys.get(i);
                }
            }

            if (!map.isEmpty())
                result += "\n" + max_key + " " + map.get(max_key);

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
