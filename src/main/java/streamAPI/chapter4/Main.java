package streamAPI.chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Main {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        System.out.println(lowCaloricDishName(menu, 400));
        System.out.println(dishesByType(menu));
        System.out.println(threeHighCaloricDishName(menu, 400));
        getMenu(menu);
        System.out.println(getDishesCount(menu));
        System.out.println(vegeterianDishes(menu));
        System.out.println(skip3_andGetHighCaloricDishName(menu));
        System.out.println(getFirstTwoMeatDishes(menu));
        System.out.println(dishNameLength(menu));

        isVegeterian(menu);
        isHealthy(menu);

        System.out.println(countTotalDishes(menu));

    }


    private static Integer countTotalDishes(List<Dish> menu) {
        long count = menu.stream().count();
        System.out.println(count);

        return menu.stream()
                .map(d -> 1)
                .reduce(0, (a, b) -> a + b);
    }

    private static void isHealthy(List<Dish> menu) {
        //allMatch
        if (menu.stream().allMatch(dish -> dish.getCalories() < 1000)) {
            System.out.println("The menu is Healthy (allMatch)");
        }

        //noneMatch
        if (menu.stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
            System.out.println("The menu is Healthy (noneMatch)");
        }

    }

    private static void isVegeterian(List<Dish> menu) {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegeterian friendly");
        }

        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        System.out.println(dish);

        Optional<Dish> dish1 = menu.stream()
                .filter(Dish::isVegetarian)
                .findFirst();
        System.out.println(dish1);
    }

    private static List<Integer> dishNameLength(List<Dish> menu) {
        return menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
    }

    private static List<Dish> getFirstTwoMeatDishes(List<Dish> menu) {
        return menu.stream()
                .filter(dish -> dish.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
    }

    private static List<Dish> skip3_andGetHighCaloricDishName(List<Dish> menu) {
        return menu.stream()
                .filter(dish -> dish.getCalories() > 400)
                .skip(3)
                .collect(Collectors.toList());
    }

    private static List<Dish> vegeterianDishes(List<Dish> menu) {
        return menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
    }

    private static long getDishesCount(List<Dish> menu) {
        return menu.stream().distinct().count();
    }

    private static void getMenu(List<Dish> menu) {
        menu.forEach(System.out::println);
    }

    private static List<String> threeHighCaloricDishName(List<Dish> menu, int caloricNumber) {
        return menu.stream()
                .filter(dish -> dish.getCalories() > caloricNumber)
                .map(Dish::getName).limit(3).collect(Collectors.toList());
    }

    private static Map<Dish.Type, List<Dish>> dishesByType(List<Dish> menu) {
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static List<String> lowCaloricDishName(List<Dish> menu, int caloricNumber) {
        return menu.stream()
                .filter(dish -> dish.getCalories() < caloricNumber)
                .map(Dish::getName)
                .collect(Collectors.toList());
    }


}
