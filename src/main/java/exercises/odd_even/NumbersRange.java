package exercises.odd_even;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumbersRange {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Start: ");
        int start = input.nextInt();
        System.out.print("\nEnter End: ");
        int end = input.nextInt();
        input.close();
        System.out.println(range(start, end));
    }

    public static List<String> range(int start, int end) {
        return IntStream
                .range(start, end + 1)
                .mapToObj(NumbersRange::oddOrEvenOrPrime)
                .collect(Collectors.toList());
    }
    private static String oddOrEvenOrPrime(int number) {
        if (isPrime(number)) {
            return String.valueOf(number);
        }
        if (isOdd(number)) {
            return("Odd");
        } else {
            return("Even");
        }
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false; //"Nor Prime Neither Composite";
        }
        int count = 2;
        while (count * count <= number) {
            if (number % count == 0) {
                return false;
            }
            count++;
        }
        return true;
    }

    public static boolean isOdd(int number) {
        return (number & 1) == 1;
    }


}
