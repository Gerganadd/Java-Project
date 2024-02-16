package bg.sofia.uni.fmi.mjt.server.command;

import bg.sofia.uni.fmi.mjt.exceptions.UnsupportedCommandException;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.UNKNOWN_COMMAND;

public enum CommandType {
    GET_FOOD("get-food"),
    GET_FOOD_REPORT_BY_ID("get-food-report"),
    GET_FOOD_BY_BARCODE("get-food-by-barcode");

    private final String text;

    CommandType(String text1) {
        text = text1;
    }

    public String getText() {
        return text;
    }

    public static CommandType getFrom(String text) {
        for (CommandType type : CommandType.values()) {
            if (type.getText().equals(text)) {
                return type;
            }
        }

        throw new UnsupportedCommandException(UNKNOWN_COMMAND + text);
    }
}
