// Gracie Driggers CSCE350
package problem2;
import java.io.*;
import java.util.*;
import java.time.*;

public class QuickSort {
    private static int HoarePartition(float[] A, int l, int r){
        float p = A[l];
        int i = l;
        int j = r+1;

        while(true) {
            do {
                i++;
            } while (i <= r && A[i] < p);

            do {
                j--;
            } while (A[j] > p);

            if (i < j) {
                float temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            } else {
                float temp = A[l];
                A[l] = A[j];
                A[j] = temp;
                return j; // Return split position
            }
        }

    }

    private static void QuickSort(float[] A, int l, int r) {
        if (l < r) {
            int s = HoarePartition(A, l, r);
            QuickSort(A, l, s - 1);
            QuickSort(A, s + 1, r);
        }
    }

    public static void main(String[] args) {
        List<Float> numbers = new ArrayList<>();

        // Read from input.txt
        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            while (scanner.hasNextFloat()) {
                numbers.add(scanner.nextFloat());
            }
        } catch (IOException e) {
            System.out.println("Error reading input file.");
            return;
        }

        float[] arr = new float[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            arr[i] = numbers.get(i);
        }

        // Measure execution time (excluding I/O)
        Instant start = Instant.now();

        QuickSort(arr, 0, arr.length - 1);

        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toNanos(); // nanoseconds

        // Write to output.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            for (float num : arr) {
                writer.print(num + " ");
            }
        } catch (IOException e) {
            System.out.println("Error writing output file.");
        }

        // Print execution time
        System.out.println("Execution time (nanoseconds): " + timeElapsed);
    }
}
