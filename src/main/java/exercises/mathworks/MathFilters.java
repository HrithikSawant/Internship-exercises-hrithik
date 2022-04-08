package exercises.mathworks;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

enum PredicateChainType {
    AND,
    OR
}

public class MathFilters {

    public static List<Integer> filter(List<Integer> list, Predicate<Integer> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public static List<Integer> withAllCommands(List<Integer> list, List<Predicate<Integer>> commands) {
        Predicate<Integer> predicate = constructPredicate(commands, PredicateChainType.AND);
        if (predicate == null) return list;
        return filter(list, predicate);
    }

    public static List<Integer> withAnyCommands(List<Integer> list, List<Predicate<Integer>> commands) {
        Predicate<Integer> predicate = constructPredicate(commands, PredicateChainType.OR);
        if (predicate == null) return list;
        return filter(list, predicate);
    }

    private static Predicate<Integer> constructPredicate(List<Predicate<Integer>> commands, PredicateChainType chainType) {
        if (commands.size() < 1) return null;
        Predicate<Integer> predicate = commands.get(0);
        if (predicate == null) return null;
        //constructing predicate
        for (int i = 1; i < commands.size(); i++) {
            Predicate<Integer> currentPredicate = commands.get(i);
            if (currentPredicate != null)
                if (chainType == PredicateChainType.AND)
                    predicate = predicate.and(currentPredicate);
                else if (chainType == PredicateChainType.OR)
                    predicate = predicate.or(currentPredicate);
        }
        return predicate;
    }





}
