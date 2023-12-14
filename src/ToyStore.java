import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyStore {

    public static void main(String[] args) throws IOException {
        PriorityQueue<Toy> toysQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t2.weight, t1.weight));

        addToy(toysQueue, "1", "Робот", 2);
        addToy(toysQueue, "2", "Конструктор", 2);
        addToy(toysQueue, "3", "Кукла", 6);

        writeResultsToFile(toysQueue, "output.txt");
    }

    private static void addToy(PriorityQueue<Toy> toysQueue, String id, String name, int weight) {
        Toy toy = new Toy(Integer.parseInt(id), name, weight);
        toysQueue.add(toy);
    }

    private static void writeResultsToFile(PriorityQueue<Toy> toysQueue, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Toy selectedToy = getRandomToy(toysQueue, random);
            writer.write(selectedToy.id + ", " + selectedToy.name + ", " + selectedToy.weight);
            writer.newLine();
        }

        writer.close();
    }

    private static Toy getRandomToy(PriorityQueue<Toy> toysQueue, Random random) {
        int totalWeight = toysQueue.stream().mapToInt(t -> t.weight).sum();
        int randomWeight = random.nextInt(totalWeight) + 1;

        int currentWeight = 0;
        for (Toy toy : toysQueue) {
            currentWeight += toy.weight;
            if (randomWeight <= currentWeight) {
                return toy;
            }
        }

        return toysQueue.peek();
    }
}