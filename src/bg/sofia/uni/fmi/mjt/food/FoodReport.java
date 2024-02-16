package bg.sofia.uni.fmi.mjt.food;

import java.io.Serializable;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.FOOD_REPORT_NULL_OR_EMPTY;

public record FoodReport(long fdcId, String ingredients, NutrientsReport labelNutrients) implements Serializable {
    private static final int INDEX_ID = 0;
    private static final int INDEX_INGREDIENTS = 1;
    private static final int INDEX_NUTRIENT_REPORT = 2;
    private static final String DELIMITER = "@";

    @Override
    public String toString() {
        return String.format("FdcId : %d%nIngredients : %s%n%s",
                fdcId, ingredients, labelNutrients);
    }

    public static FoodReport parseFoodReport(String text) {
        validateText(text);

        String[] args = text.split(DELIMITER);

        long id = Long.parseLong(args[INDEX_ID]);
        String ingredients = args[INDEX_INGREDIENTS];
        NutrientsReport report = NutrientsReport.parseNutrientReport(args[INDEX_NUTRIENT_REPORT]);

        return new FoodReport(id, ingredients, report);
    }

    private static void validateText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(FOOD_REPORT_NULL_OR_EMPTY);
        }
    }

    public String formatFoodReport() {
        return String.join(DELIMITER,
                Long.toString(fdcId),
                ingredients,
                labelNutrients.formatNutrientsReport());
    }
}
