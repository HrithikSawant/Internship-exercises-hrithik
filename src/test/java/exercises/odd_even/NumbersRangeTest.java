package exercises.odd_even;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NumbersRangeTest {

    @Test
    public void rangeTest(){
        List<String> output = NumbersRange.range(5,10);
        List<String> expected = new ArrayList<>(Arrays.asList("5", "Even","7", "Even", "Odd", "Even"));
        assertThat(output, is(expected));
    }


}
