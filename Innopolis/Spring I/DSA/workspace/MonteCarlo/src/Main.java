import java.awt.geom.Point2D;
import java.io.*;

/**
 * Created by worm2fed on 05.02.17.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Point2D> array = new ArrayList<Point2D>();
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;
        String line;

        try {
            bf_reader = new BufferedReader(new FileReader("input.txt"));

            while ((line = bf_reader.readLine()) != null) {
                String[] cur_line = line.split(" ");

                for (int i = 0; i < cur_line.length - 1; i = i + 2)
                    array.add(new Point2D.Double(Double.parseDouble(cur_line[i]), Double.parseDouble(cur_line[i + 1])));

                try {
                    bf_writer = new BufferedWriter(new FileWriter("output.txt"));
                    MonteCarlo monteCarlo = new MonteCarlo(array);
                    bf_writer.write(monteCarlo.calculate());
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
