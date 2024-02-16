package bg.sofia.uni.fmi.mjt.food_api.responds;

import bg.sofia.uni.fmi.mjt.food.Food;

import java.util.List;

public record RespondFoods(List<Food> foods) {
    private static final String SEPARATOR = "-".repeat(40);

    @Override
    public String toString() { // to-do refactor
        StringBuilder builder = new StringBuilder();
        foods.forEach(x -> builder.append(String.format("%s%n%s", SEPARATOR, x.toString())));

        return builder.toString();
    }
}
