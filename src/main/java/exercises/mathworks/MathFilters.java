package exercises.mathworks;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

enum PredicateChainType {
    AND,
    OR
}

public class MathFilters {

    private static HashMap<String, Predicate<Integer>> preferences;
    private static HashMap<String, BiPredicate<String,Predicate<Integer>>> preferences1;

    MathFilters() {
        preferences1 = new HashMap<>();
        preferences = new HashMap<>();
        preferences.put("even",even());
        preferences.put("odd",odd());
        preferences.put("prime",prime());
    }


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
