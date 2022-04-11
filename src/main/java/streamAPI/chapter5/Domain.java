package streamAPI.chapter5;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Domain {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //Find all transactions in the year 2011 and sort them by value (small to high).
        System.out.println(findTransactions(transactions, 2011));

        //  What are all the unique cities where the traders work?
        System.out.println(uniquesCities(transactions));

        //Find all traders from Cambridge and sort them by name.
        System.out.println(cambridgeTraders(transactions));

        //Return a string of all traders’ names sorted alphabetically.
        System.out.println(tradersNames(transactions));

        //Are any traders based in Milan?
        System.out.println(tradersInMilan(transactions));

        // Print all transactions’ values from the traders living in Cambridge.
        cambridgeTradersTransactions(transactions);

        // What’s the highest value of all the transactions?
        System.out.println(highestTransaction(transactions));

        // Find the transaction with the smallest value.
        System.out.println(lowestTransaction(transactions));


    }

    private static Optional<Transaction> lowestTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

    private static Optional<Integer> highestTransaction(List<Transaction> transactions) {
        Optional<Transaction> smallestTransaction =
                transactions.stream()
                        .min(Comparator.comparing(Transaction::getValue));

        Optional<Integer> reduce = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        IntStream re = transactions.stream()
                .mapToInt(Transaction::getValue);
        Stream<Integer> boxed = re.boxed();


        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();

        int maximum = max.orElse(1);

        return transactions.stream()
                .map(Transaction::getValue)
                .reduce((a, b) -> a > b ? a : b);

    }

    private static void cambridgeTradersTransactions(List<Transaction> transactions) {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    private static boolean tradersInMilan(List<Transaction> transactions) {
        return transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
    }

    private static String tradersNames(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
//                .collect(joining());
    }

    private static List<Trader> cambridgeTraders(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
    }

    private static List<String> uniquesCities(List<Transaction> transactions) {
        return transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
    }

    private static List<Transaction> findTransactions(List<Transaction> transactions, int year) {
        return transactions.stream()
                .filter(t -> t.getYear() == year)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
    }


}
