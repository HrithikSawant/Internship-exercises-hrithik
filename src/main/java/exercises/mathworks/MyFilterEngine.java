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
    private final Integer x;

    public LessThanX(Integer x) {
        this.x = x;
    }

    @Override
    public boolean apply(Integer num) {
        return num < x;
    }
}


public class MyFilterEngine {

    public Predicate<Integer> allMatchConstruct(List<Predicate<Integer>> commands) {
        return commands.stream().reduce(Predicate::and).get();
    }
    public Predicate<Integer> anyMatchConstruct(List<Predicate<Integer>> commands) {
        return commands.stream().reduce(Predicate::or).get();
    }

    public List<Integer> filter(List<Integer> list, Predicate<Integer> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Integer> filterWithAllMatches(List<Integer> numbers, List<Command> commands) {
        return numbers.stream().filter(number -> commands.stream()
                .allMatch(command -> command.apply(number))).collect(Collectors.toList());
    }

    public  List<Integer> filterWithAnyMatches(List<Integer> numbers, List<Command> commands) {
        return numbers.stream().filter(number -> commands.stream()
                .anyMatch(command -> command.apply(number))).collect(Collectors.toList());
    }
}
