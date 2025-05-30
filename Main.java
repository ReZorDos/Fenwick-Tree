import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int size = 10_000;
        int[] randomArray = generateRandomArray(size, 0, 100);

        FenwickTree fenwickTree = new FenwickTree(size);

        System.out.println("Добавление элементов с замерами времени:");
        System.out.println("Индекс | Время (нс) | Операции");
        System.out.println("------------------------------");

        long totalInsertTime = 0;
        int totalInsertOperations = 0;

        for (int i = 0; i < size; i++) {
            int value = randomArray[i];

            long startTime = System.nanoTime();
            fenwickTree.update(i, value);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalInsertTime += duration;

            int operationsEstimate = (int) (Math.log(i + 1) / Math.log(2)) + 1;
            totalInsertOperations += operationsEstimate;

            System.out.printf("%6d | %9d | %8d%n", i, duration, operationsEstimate);
        }

        double avgInsertTime = (double) totalInsertTime / size;
        double avgInsertOperations = (double) totalInsertOperations / size;

        // Проверка корректности - сумма всех элементов
        int totalSum = fenwickTree.query(size - 1);
        System.out.println("\nОбщая сумма всех элементов: " + totalSum);

        System.out.println("\nПоиск 100 случайных элементов:");
        System.out.println("Сумма | Время (нс) | Операции");
        System.out.println("------------------------------");

        long totalSearchTime = 0;
        int totalSearchOperations = 0;

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int sumToFind = random.nextInt(totalSum + 1);

            long startTime = System.nanoTime();
            int index = fenwickTree.find(sumToFind);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalSearchTime += duration;

            int operationsEstimate = (int) (Math.log(size) / Math.log(2)) + 1;
            totalSearchOperations += operationsEstimate;

            System.out.printf("%5d | %9d | %8d%n", sumToFind, duration, operationsEstimate);
        }

        double avgSearchTime = (double) totalSearchTime / 100;
        double avgSearchOperations = (double) totalSearchOperations / 100;

        System.out.println("\nУдаление 1000 случайных элементов:");
        System.out.println("Индекс | Значение | Время (нс) | Операции");
        System.out.println("------------------------------------------");

        long totalRemoveTime = 0;
        int totalRemoveOperations = 0;

        Set<Integer> indicesToRemove = new HashSet<>();
        while (indicesToRemove.size() < 1000) {
            indicesToRemove.add(random.nextInt(size));
        }

        for (int index : indicesToRemove) {
            int value = fenwickTree.rangeQuery(index, index);

            long startTime = System.nanoTime();
            fenwickTree.remove(index, value);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalRemoveTime += duration;

            int operationsEstimate = (int) (Math.log(size) / Math.log(2)) + 1;
            totalRemoveOperations += operationsEstimate;

            System.out.printf("%6d | %8d | %9d | %8d%n",
                    index, value, duration, operationsEstimate);
        }

        double avgRemoveTime = (double) totalRemoveTime / 1000;
        double avgRemoveOperations = (double) totalRemoveOperations / 1000;

        int remainingSum = fenwickTree.query(size - 1);
        System.out.println("\nСумма оставшихся элементов: " + remainingSum);
        System.out.println("Сумма удаленных элементов: " + (totalSum - remainingSum));

        System.out.println("\nСредние показатели:");
        System.out.println("------------------------------------------");
        System.out.printf("Вставка:   Время = %.2f нс, Операций = %.2f%n", avgInsertTime, avgInsertOperations);
        System.out.printf("Поиск:     Время = %.2f нс, Операций = %.2f%n", avgSearchTime, avgSearchOperations);
        System.out.printf("Удаление:  Время = %.2f нс, Операций = %.2f%n", avgRemoveTime, avgRemoveOperations);
    }

    private static int[] generateRandomArray(int size, int min, int max) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }
}