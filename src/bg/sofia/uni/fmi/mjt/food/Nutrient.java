package bg.sofia.uni.fmi.mjt.food;

import bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages;

public record Nutrient(double value) {
    private static final String MATCH_COMMA = ",";
    private static final String MATCH_DOT = ".";

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    public static Nutrient parseNutrient(String text) {
        validateText(text);

        text = text.trim().replaceAll(MATCH_COMMA, MATCH_DOT);

        return new Nutrient(Double.parseDouble(text));
    }

    private static void validateText(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessages.NUTRIENT_VALUE_NULL_OR_EMPTY);
        }
    }

    public String formatNutrient() {
        return String.format("%.2f", value);
    }
}
