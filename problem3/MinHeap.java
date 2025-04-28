package problem3;
import java.io.*;
import java.util.*;
import java.time.*;

public class MaxHeap {

    private static void HeapBottomUp(int[] H, int n) {
        for (int i = n / 2; i >= 1; i--) {
            int k = i;
            int v = H[k];
            boolean heap = false;

            while(!heap && 2 * k <= n) {
                int j = 2 * k;
                if (j < n) {
                    if (H[j] > H[j + 1]) {
                        j = j + 1;
                    }
                }
                if (v <= H[j]) {
                    heap = true;
                } else {
                    H[k] = H[j];
                    k = j;
                }
            }
            H[k] = v;
        }
    }

    public static void main(String[] args) {
        int n;
        int[] H;

        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            n = scanner.nextInt();
            H = new int[n + 1]; // H[1..n], H[0] unused
            for (int i = 1; i <= n; i++) {
                H[i] = scanner.nextInt();
            }
        } catch (IOException e) {
            System.out.println("Error reading input file.");
            return;
        }

        Instant start = Instant.now();

        HeapBottomUp(H, n);

        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toNanos();

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            for (int i = 1; i <= n; i++) {
                writer.print(H[i] + " ");
            }
        } catch (IOException e) {
            System.out.println("Error writing output file.");
        }

        System.out.println("Execution time (nanoseconds): " + timeElapsed);
    }
}