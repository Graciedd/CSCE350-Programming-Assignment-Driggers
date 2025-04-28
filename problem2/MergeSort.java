// Gracie Driggers CSCE350
package problem2;
import java.io.*;
import java.util.*;
import java.time.*;

public class MergeSort{

private static void merge(float[] B, float[] C, float[] A){
    int i = 0; j = 0; k = 0;
    int bLength = B.length;
    int cLength = C.length;
    while (i < bLength && j < cLength) {
        if B[i] <= C[j] {
            A[k++] = B[i++];
        } else {
            A[k++] = C[j++];
        }
    }

    while (i < bLength) {
        A[k++] = B[i++];
    }
    while (j < cLength) {
        A[k++] = C[j++];
    }
}

private static void Mergesort(float[] A){
    int n = A.length;
    if (n > 1)
        int mid = n/2;

    float[] B = Arrays.copyOf(A, 0, mid);
    float[] C = Arrays.copyOf(A, mid, n);
    Mergesort(B);
    Mergesort(C);
    merge(B,C,A);
}

public static void main(String[] args) {
    List<Float> numbers = new ArrayList<>();
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
    mergeSort(arr);

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