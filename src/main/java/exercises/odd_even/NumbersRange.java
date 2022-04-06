package exercises.odd_even;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        List<String> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {

            if (isPrime(i)) {
                result.add(String.valueOf(i));
                continue;
            }

            if (isOdd(i)) {
                result.add("Odd");
            } else {
                result.add("Even");
            }
        }
        return result;
    }

    public static boolean isPrime(int number) {
//        if (number <= 1) {
//            return false; //"Nor Prime Neither Composite";
//        }
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
