package bg.sofia.uni.fmi.mjt.food;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.NUTRIENT_REPORT_NULL_OR_EMPTY;

public record NutrientsReport(Nutrient fat, Nutrient fiber, Nutrient sugars,
                              Nutrient protein, Nutrient calories) {
    private static final int INDEX_FAT = 0;
    private static final int INDEX_FIBER = 1;
    private static final int INDEX_SUGARS = 2;
    private static final int INDEX_PROTEIN = 3;
    private static final int INDEX_CALORIES = 4;
    private static final String DELIMITER = "#";

    @Override
    public String toString() {
        return String.format("Calories : %s Protein : %s Sugars : %s Fiber : %s Fat : %s",
                calories, protein, sugars, fiber, fat);
    }

    public static NutrientsReport parseNutrientReport(String text) {
        validateText(text);

        String[] args = text.split(DELIMITER);

        Nutrient fat = Nutrient.parseNutrient(args[INDEX_FAT]);
        Nutrient fiber = Nutrient.parseNutrient(args[INDEX_FIBER]);
        Nutrient sugars = Nutrient.parseNutrient(args[INDEX_SUGARS]);
        Nutrient protein = Nutrient.parseNutrient(args[INDEX_PROTEIN]);
        Nutrient calories = Nutrient.parseNutrient(args[INDEX_CALORIES]);

        return new NutrientsReport(fat, fiber, sugars, protein, calories);
    }

    public String formatNutrientsReport() {
        return String.join(DELIMITER,
                fat.formatNutrient(),
                fiber.formatNutrient(),
                sugars.formatNutrient(),
                protein.formatNutrient(),
                calories.formatNutrient());
    }

    private static void validateText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(NUTRIENT_REPORT_NULL_OR_EMPTY);
        }
    }
}
