package exercises.mathworks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;



class OddCommand implements Command {
    @Override
    public boolean apply(Integer num) {
        return num % 2 == 1;
    }
}

class PrimeCommand implements Command {
    @Override
    public boolean apply(Integer num) {
        return num > 1 && IntStream.rangeClosed(2, num / 2).noneMatch(i -> num % i == 0);
    }
}

class GreaterThanX implements Command {

    //closure
    private final Integer x;

    public GreaterThanX(Integer x) {
        this.x = x;
    }

    @Override
    public boolean apply(Integer num) {
        return num > x;
    }
}

class MultipleOfX implements Command {
    //closure
    private final Integer x;

    public MultipleOfX(Integer x) {
        this.x = x;
    }

    @Override
    public boolean apply(Integer num) {
        return num % x == 0;
    }
}

public class MathFiltersTest {

    MyFilterEngine engine = new MyFilterEngine();

    @Test
    public void testGetEvenNumbers() {

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
        List<Integer> evenNumbers = new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10, 12));
        List<Command> command = new ArrayList<>(List.of(new EvenCommand()));

        List<Integer> actual = engine.filterWithAllMatches(numbers, command);

        assertThat(actual, is(evenNumbers));
    }

    @Test
    public void testGetOddNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
        List<Integer> oddNumbers = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9, 11, 13));
        List<Command> command = new ArrayList<>(List.of(new OddCommand()));

        List<Integer> actual = engine.filterWithAllMatches(numbers, command);

        assertThat(actual, is(oddNumbers));
    }


    @Test
    public void testGetPrimeNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19));
        List<Integer> primeNumbers = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19));
        List<Command> command = new ArrayList<>(List.of(new PrimeCommand()));

        List<Integer> actual = engine.filterWithAllMatches(numbers, command);

        assertThat(actual, is(primeNumbers));

    }

    @Test
    public void testGetOddPrimeNumbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19));
        List<Integer> oddPrimeNumbers = new ArrayList<>(Arrays.asList(3, 5, 7, 11, 13, 17, 19));
        List<Command> command = new ArrayList<>(List.of(new OddCommand(), new PrimeCommand()));

        List<Integer> actual = engine.filterWithAllMatches(numbers, command);

        assertThat(actual, is(oddPrimeNumbers));
    }

    @Test
    public void testGetEvenMultiple_Numbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected = new ArrayList<>(Arrays.asList(10, 20));
        List<Command> command = new ArrayList<>(List.of(new EvenCommand(), new MultipleOfX(10)));

        List<Integer> actual = engine.filterWithAllMatches(numbers, command);

        assertThat(actual, is(expected));
    }

    @Test
    public void testGetOddMultipleAndGreaterThan_Numbers() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected = new ArrayList<>(List.of(15));
        List<Command> command = new ArrayList<>(List.of(new OddCommand(), new MultipleOfX(3), new GreaterThanX(10)));

        List<Integer> actual = engine.filterWithAllMatches(numbers, command);

        assertThat(actual, is(expected));
    }


    @Test
    public void testAll() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAll = new ArrayList<>(List.of(9, 15));
        List<Integer> expected_withAll_1 = new ArrayList<>(List.of(6, 12));

        List<Command> commands1 = new ArrayList<>(List.of(new OddCommand(), new GreaterThanX(5), new MultipleOfX(3)));
        List<Integer> actualTest1 = engine.filterWithAllMatches(numbers, commands1);

        List<Command> commands2 = new ArrayList<>(List.of(new EvenCommand(), new LessThanX(15), new MultipleOfX(3)));
        List<Integer> actualTest2 = engine.filterWithAllMatches(numbers, commands2);

        assertThat(actualTest1, is(expected_withAll));
        assertThat(actualTest2, is(expected_withAll_1));
    }

    @Test
    public void testAny() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAny = new ArrayList<>(List.of(2, 3, 5, 7, 10, 11, 13, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAny_1 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 9, 12, 15, 18));

        List<Command> commands1 = new ArrayList<>(List.of(new PrimeCommand(), new GreaterThanX(15),
                new MultipleOfX(5)));
        List<Integer> actualTest1 = engine.filterWithAnyMatches(numbers, commands1);
        List<Command> commands2 = new ArrayList<>(List.of(new LessThanX(6), new MultipleOfX(3)));
        List<Integer> actualTest2 = engine.filterWithAnyMatches(numbers, commands2);

        assertThat(actualTest1, is(expected_withAny));
        assertThat(actualTest2, is(expected_withAny_1));
    }

    @Test
    public void testAllForPredicates() {

        Predicate<Integer> even = x -> x % 2 == 0;
        Predicate<Integer> lessThan17 = x -> x < 17 ;
        Predicate<Integer> greaterThan5 = x -> x > 5 ;

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAny = new ArrayList<>(List.of(6, 8, 10, 12, 14, 16));

        List<Predicate<Integer>> commands =
                new ArrayList<>(List.of(even,greaterThan5, lessThan17));

        Predicate<Integer> allMatchConstruct = engine.allMatchConstruct(commands);
        List<Integer> actualTest1 = engine.filter(numbers,allMatchConstruct);

        assertThat(actualTest1, is(expected_withAny));
    }

    @Test
    public void testAnyForPredicates() {

        Predicate<Integer> multipleOf3 = x -> x % 3 == 0;
        Predicate<Integer> lessThan6 = x -> x < 6 ;

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        List<Integer> expected_withAny = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 9, 12, 15, 18));

        List<Predicate<Integer>> commands =
                new ArrayList<>(List.of(lessThan6, multipleOf3));

        Predicate<Integer> anyMatchConstruct = engine.anyMatchConstruct(commands);
        List<Integer> actualTest = engine.filter(numbers,anyMatchConstruct);

        assertThat(actualTest, is(expected_withAny));
    }



}