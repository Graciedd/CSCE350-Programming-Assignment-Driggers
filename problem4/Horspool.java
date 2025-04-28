import java.io.*;
import java.util.*;
import java.time.*;

public class Horspool {

    private static int[] shiftTable(String pattern) {
        int m = pattern.length();
        int[] table = new int[128]; // Assuming ASCII

        for (int i = 0; i < 128; i++) {
            table[i] = m;
        }
        for (int j = 0; j < m - 1; j++) {
            table[pattern.charAt(j)] = m - 1 - j;
        }
        return table;
    }

    private static int HorspoolStringMatching(String pattern, String text) {
        int[] table = shiftTable(pattern);
        int m = pattern.length();
        int n = text.length();
        int i = m - 1; // Position of the pattern's right end

        while (i <= n - 1) {
            int k = 0; // number of matched characters
            while (k <= m - 1 && pattern.charAt(m - 1 - k) == text.charAt(i - k)) {
                k++;
            }
            if (k == m) {
                return i - m + 1; // match found
            } else {
                i = i + table[text.charAt(i)];
            }
        }
        return -1; // no match
    }

    public static void main(String[] args) {
        String pattern = "";
        String text = "";

        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            pattern = scanner.nextLine();
            text = scanner.nextLine();
        } catch (IOException e) {
            System.out.println("Error reading input file.");
            return;
        }

        Instant start = Instant.now();

        int position = HorspoolStringMatching(pattern, text);

        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toNanos();

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            writer.println(position);
        } catch (IOException e) {
            System.out.println("Error writing output file.");
        }

        System.out.println("Execution time (nanoseconds): " + timeElapsed);
    }
}