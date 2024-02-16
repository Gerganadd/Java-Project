package bg.sofia.uni.fmi.mjt.server.command;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public record Command(CommandType type, String... parameters) {
    private static final int INDEX_COMMAND_TYPE = 0;
    private static final String SEPARATOR = " ";
    private static final String DELIMITER = ", ";

    @Override
    public String toString() {
        String formattedParameters = String.join(DELIMITER, parameters);

        return String.format("Command : %s%nParameters : %s%n", type, formattedParameters);
    }

    public String getValue() {
        return String.join(SEPARATOR, parameters).trim();
    }

    public static Command parse(ByteBuffer buffer) {
        buffer.flip();

        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        String content = new String(clientInputBytes, StandardCharsets.UTF_8)
                .trim();

        String[] values = content.split(SEPARATOR);
        CommandType type = CommandType.getFrom(values[INDEX_COMMAND_TYPE]);

        String[] args = Arrays.stream(values)
                .skip(1) // to-do
                .toArray(String[]::new);

        return new Command(type, args);
    }
}
