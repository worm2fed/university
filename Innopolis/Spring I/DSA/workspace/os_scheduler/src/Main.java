import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by worm2fed on 20.03.17.
 */
public class Main {
    public static void main(String[] args) {
        String line = null;
        BufferedReader bf_reader = null;
        BufferedWriter bf_writer = null;

        List<Event> event_list = new ArrayList<>();

        try {
            bf_reader = new BufferedReader(new FileReader("input.csv"));
            // Skip first line, because it is headers
            bf_reader.readLine();
            // Add events to heap
            while ((line = bf_reader.readLine()) != null) {
                String[] row = line.split(",");

                try {
                    // Add events to list
                    event_list.add(new Event(row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2]), Integer.parseInt(row[3])));
                } catch (NumberFormatException e) {
                    System.out.println("not_int");
                }
            }

            // Sort events with time
            event_list.sort(Comparator.comparingInt(Event::getTime));

            try {
                bf_writer = new BufferedWriter(new FileWriter("output.txt"));
                bf_writer.write(getLastApp(event_list, 120000));
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

    // Get last application that was able to finish
    private static String getLastApp(List<Event> event_list, int limit) {
        BinaryHeap<Event> queue = new BinaryHeap<>();
        Event in_progress = null;
        String last_finished = "";

        for (int timer = 0, i = 0, last_start = 0; timer <= limit; timer++) {
            // Get event if exist
            if (i < event_list.size()) {
                Event event = event_list.get(i);

                // Add to queue when required
                if (event.getTime() == timer) {
                    System.out.println(timer + "ms, " + event.getName() + " -- in queue");
                    queue.add(event);
                    i++;
                }
            }

            // Start event
            if (in_progress == null) {
                if (!queue.isEmpty()) {
                    in_progress = queue.get();
                    System.out.println(timer + "ms, " + in_progress.getName() + " -- started");
                    last_start = timer;
                }
            } else {
                // Finish event
                if (last_start + in_progress.getCpu() == timer) {
                    System.out.println(timer + "ms, " + in_progress.getName() + " -- finished");
                    last_finished = in_progress.getName();
                    in_progress = null;
                    timer--;
                }
            }
        }

        return last_finished;
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
