// MaximumBipartiteMatching.java
import java.io.*;
import java.util.*;
import java.time.*;

public class MaximumBipartiteMatching {

    private static Map<String, String> match = new HashMap<>(); // matched pairs
    private static Map<String, String> label = new HashMap<>(); // labels for augmenting paths

    public static void main(String[] args) {
        List<String> V = new ArrayList<>();
        List<String> U = new ArrayList<>();
        Map<String, List<String>> adj = new HashMap<>(); // adjacency list

        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            V = Arrays.asList(scanner.nextLine().split("\\s+"));
            U = Arrays.asList(scanner.nextLine().split("\\s+"));

            for (String v : V) {
                adj.put(v, new ArrayList<>());
            }
            for (String u : U) {
                adj.put(u, new ArrayList<>());
            }

            for (int i = 0; i < V.size(); i++) {
                for (int j = 0; j < U.size(); j++) {
                    if (scanner.hasNextInt() && scanner.nextInt() == 1) {
                        adj.get(V.get(i)).add(U.get(j));
                        adj.get(U.get(j)).add(V.get(i));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input file.");
            return;
        }

        Instant start = Instant.now();

        // initialize matching as empty
        Queue<String> queue = new LinkedList<>();

        while (true) {
            label.clear();
            queue.clear();

            // enqueue all free vertices in V
            for (String v : V) {
                if (!match.containsKey(v)) {
                    queue.add(v);
                }
            }

            boolean augmented = false;

            while (!queue.isEmpty()) {
                String w = queue.poll();

                if (V.contains(w)) { // w in V
                    for (String u : adj.get(w)) {
                        if (!match.containsKey(u)) {
                            // augment matching
                            match.put(w, u);
                            match.put(u, w);

                            // follow labels back to fix matching
                            String v = w;
                            while (label.containsKey(v)) {
                                String prevU = label.get(v);
                                match.remove(v);
                                match.remove(prevU);
                                v = label.get(prevU);
                                match.put(v, prevU);
                                match.put(prevU, v);
                            }

                            augmented = true;
                            break; // exit the for loop
                        } else if (match.get(w) != null && match.get(w).equals(u)) {
                            continue; // skip already matched edges
                        } else if (!label.containsKey(u)) { // u matched, u not labeled
                            label.put(u, w);
                            queue.add(u);
                        }
                    }
                } else { // w ∈ U (w is a U vertex and matched)
                    String mateV = match.get(w);
                    if (mateV != null && !label.containsKey(mateV)) {
                        label.put(mateV, w);
                        queue.add(mateV);
                    }
                }
            }

            if (!augmented) {
                break; // no more augmenting paths
            }
        }

        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toNanos();

        // Output matching
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            Set<String> printed = new HashSet<>();
            for (String v : V) {
                if (match.containsKey(v) && !printed.contains(v)) {
                    String u = match.get(v);
                    writer.println(v + " " + u);
                    printed.add(v);
                    printed.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing output file.");
        }

        System.out.println("Execution time (nanoseconds): " + timeElapsed);
    }
}
