package exercises.mathworks;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.core.Is.is;

public class MathFiltersTest  {

    public static Predicate<Integer> even() {
        return number -> (number & 1) == 0;
    }

    public static Predicate<Integer> odd() {
        return even().negate();
    }

    public static Predicate<Integer> prime() {
        return number -> {
            if (number <= 1) { return false;}
            int count = 2;
            while (count * count <= number) {
                if (number % count == 0) {return false;}
                count++;
            }
            return true;
        };
    }

    private static Predicate<Integer> getMultipleOf(int multiple) {
        return number -> number % multiple == 0;
    }

    private static Predicate<Integer> greaterThan(int greater) {
        return number -> number > greater;
    }

    private static Predicate<Integer> lessThan(int lessThan) {
        return number -> number < lessThan;
    }

    public static Predicate<Integer> getOddPrimeNumbers() {
        return odd().and(prime());
    }

    public static Predicate<Integer> getEvenAndMultipleOf(int multiple) {
        return even().and(getMultipleOf(multiple));
    }

    public static Predicate<Integer> getOddMultipleAndGreater(int multiple, int greater) {
        return odd().and(getMultipleOf(multiple)).and(greaterThan(greater));
    }

    @Test
    public void testGetEvenNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13));
        List<Integer> evenNumbers = new ArrayList<>(Arrays.asList(2,4,6,8,10,12));

        List<Integer> actual = MathFilters.filter(numbers,even());

        Assert.assertThat(actual, is(evenNumbers));
    }

    @Test
    public void testGetOddNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13));
        List<Integer> oddNumbers = new ArrayList<>(Arrays.asList(1,3,5,7,9,11,13));

        List<Integer> actual = MathFilters.filter(numbers,odd());

        Assert.assertThat(actual, is(oddNumbers));
    }

    @Test
    public void testGetPrimeNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19));
        List<Integer> primeNumbers = new ArrayList<>(Arrays.asList(2,3,5,7,11,13,17,19));

        List<Integer> actual = MathFilters.filter(numbers,prime());

        Assert.assertThat(actual, is(primeNumbers));
    }

    @Test
    public void testGetOddPrimeNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19));
        List<Integer> oddPrimeNumbers = new ArrayList<>(Arrays.asList(3,5,7,11,13,17,19));

        List<Integer> actual = MathFilters.filter(numbers,getOddPrimeNumbers());

        Assert.assertThat(actual, is(oddPrimeNumbers));
    }

    @Test
    public void testGetEvenMultiple_Numbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected = new ArrayList<>(Arrays.asList(10, 20));

        List<Integer> actual = MathFilters.filter(numbers, getEvenAndMultipleOf(5));
        Assert.assertThat(actual, is(expected));
    }

    @Test
    public void testGetOddMultipleAndGreaterThan_Numbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected = new ArrayList<>(List.of(15));

        List<Integer> actual = MathFilters.filter(numbers,getOddMultipleAndGreater(3,10));
        Assert.assertThat(actual, is(expected));
    }



  @Test
    public void testAll() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAll = new ArrayList<>(List.of(9,15));
        List<Integer> expected_withAll_1 = new ArrayList<>(List.of(6, 12));


        List<Integer> actualTest1 = MathFilters.withAllCommands(numbers,List.of(odd(),greaterThan(5),getMultipleOf(3)));
        Assert.assertThat(actualTest1, is(expected_withAll));

        List<Integer> actualTest2 = MathFilters.withAllCommands(numbers,List.of(even(),lessThan(15),getMultipleOf(3)));
        Assert.assertThat(actualTest2, is(expected_withAll_1));
    }

    @Test
    public void testAny() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAny = new ArrayList<>(List.of(2, 3, 5, 7, 10, 11, 13, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAny_1 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 9, 12, 15, 18));

        List<Integer> actualTest1 = MathFilters.withAnyCommands(numbers,List.of(prime(),greaterThan(15),getMultipleOf(5)));
        Assert.assertThat(actualTest1, is(expected_withAny));

        List<Integer> actualTest2 = MathFilters.withAnyCommands(numbers,List.of(lessThan(6),getMultipleOf(3)));
        Assert.assertThat(actualTest2, is(expected_withAny_1));
    }

}