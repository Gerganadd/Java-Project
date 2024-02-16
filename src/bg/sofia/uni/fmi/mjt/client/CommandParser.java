package bg.sofia.uni.fmi.mjt.client;

import bg.sofia.uni.fmi.mjt.exceptions.UnsupportedCommandException;

import java.util.Arrays;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.client.CommandType.UNKNOWN;
import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.ONLY_DIGITS;
import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.ONLY_LETTERS;

public class CommandParser { // or command type parser
    private static final int INDEX_OF_COMMAND_TYPE = 0;
    private static final int INDEX_OF_CODE = 1;
    private static final int INDEX_OF_ID = 1;
    private static final String SEPARATOR = " ";

    private static final String REGEX_DIGITS = "\\d+";
    private static final String REGEX_LETTERS = "[a-zA-Z]+";

    public static CommandType parse(String command) {
        if (command == null || command.isBlank()) {
            return UNKNOWN;
        }

        String[] args = command.trim().split(SEPARATOR);

        CommandType commandType = CommandType.getFrom(args[INDEX_OF_COMMAND_TYPE]);

        switch (commandType) {
            case GET_FOOD_BY_NAME -> validateCommandArguments(args);
            case GET_FOOD_REPORT_BY_FCD_ID -> validateIsOnlyDigits(args[INDEX_OF_ID]);
            case GET_FOOD_BY_BARCODE -> validateIsOnlyDigits(args[INDEX_OF_CODE]);
        }

        return commandType;
    }

    private static void validateIsOnlyDigits(String text) {
        if (!text.matches(REGEX_DIGITS)) {
            throw new UnsupportedCommandException(ONLY_DIGITS);
        }
    }

    private static void validateCommandArguments(String[] args) {
        List<String> elements = Arrays.stream(args).skip(1).toList();

        boolean has = elements.stream().anyMatch(x -> !x.matches(REGEX_LETTERS));

        if (has) {
            throw new UnsupportedCommandException(ONLY_LETTERS);
        }
    }

    private CommandParser() {
        // don't want instances of this class
    }
}
