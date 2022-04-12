package exercises.mathworks;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


interface Command {
    boolean apply(Integer num);
}

class EvenCommand implements Command {
    @Override
    public boolean apply(Integer num) {
        return num % 2 == 0;
    }
}
class LessThanX implements Command {

    //closure
    private Integer x;

    public LessThanX(Integer x) {
        this.x = x;
    }

    @Override
    public boolean apply(Integer num) {
        return num < x;
    }
}

public class MyFilterEngine {

    public static List<Integer> filterWithAllMatches(List<Integer> numbers, List<Command> commands) {
        return numbers.stream().filter(number -> commands.stream()
                .allMatch(command -> command.apply(number))).collect(Collectors.toList());
    }

    public static List<Integer> filterWithAnyMatches(List<Integer> numbers, List<Command> commands) {
        return numbers.stream().filter(number -> commands.stream()
                .anyMatch(command -> command.apply(number))).collect(Collectors.toList());
    }
}
