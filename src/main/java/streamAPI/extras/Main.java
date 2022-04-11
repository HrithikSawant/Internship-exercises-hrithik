package streamAPI.extras;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");
        System.out.println(Arrays.stream(num).sum());

        System.out.println(UniqueElements(numbers));

        System.out.println(wordsCount(words));
        System.out.println(uniqueCharCount(words));
        System.out.println(squareRoot(numbers));
        List<int[]> pairs = numberPairs(numbers, numbers1);
        for (int[] pair : pairs) {
            System.out.println(Arrays.toString(pair));
        }

        List<int[]> pairsDivisibleBy = numbersPairsDivisibleBy(numbers, numbers1, 3);
        for (int[] pair : pairsDivisibleBy) {
            System.out.println(Arrays.toString(pair));
        }

        System.out.println(sum(numbers));
        System.out.println(sumWithOptional(numbers));
        maximumAndMinimum(numbers);
        capital(words);
        fibonacci(10);
        random();
    }

    private static void random() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    private static void fibonacci(int limit) {
        Stream.iterate(new int[]{0, 1},
                        t -> new int[]{t[1], t[0] + t[1]})
                .limit(limit)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        Stream.iterate(new int[]{0, 1},
                        t -> new int[]{t[1], t[0] + t[1]})
                .limit(limit)
                .map(t -> t[0])
                .forEach(System.out::print);
    }

    private static void capital(List<String> words) {
        words.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }


    private static void maximumAndMinimum(List<Integer> numbers) {
        System.out.println(numbers.stream().reduce((x, y) -> x > y ? x : y));
        System.out.println(numbers.stream().reduce(Integer::min));
    }

    private static Optional<Integer> sumWithOptional(List<Integer> numbers) {
        return numbers.stream()
                .reduce(Integer::sum);
    }

    private static int sum(List<Integer> numbers) {
//        return numbers.stream()
//                .reduce(0,(a,b) -> a+b);
        return numbers.stream()
                .reduce(0, Integer::sum);
    }


    private static List<int[]> numbersPairsDivisibleBy(List<Integer> numbers, List<Integer> numbers1, int x) {
        return numbers.stream()
                .flatMap(i -> numbers1.stream()
                        .filter(j -> (i + j) % x == 0)
                        .map(j -> new int[]{i, j})
                ).collect(Collectors.toList());
    }

    private static List<int[]> numberPairs(List<Integer> numbers, List<Integer> numbers1) {
        return numbers.stream()
                .flatMap(n -> numbers1.stream()
                        .map(j -> new int[]{n, j}))
                .collect(Collectors.toList());
    }

    private static List<Integer> squareRoot(List<Integer> numbers) {
        return numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    private static List<String> uniqueCharCount(List<String> words) {
        return words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct().collect(Collectors.toList());
    }

    private static List<Integer> wordsCount(List<String> words) {
        return words.stream().map(String::length).collect(Collectors.toList());
    }

    private static List<Integer> UniqueElements(List<Integer> numbers) {
        return numbers.stream().distinct().collect(Collectors.toList());
    }
}
