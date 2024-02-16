package bg.sofia.uni.fmi.mjt.food;

import java.io.Serializable;

public record Food(long fdcId, String description, String gtinUpc) implements Serializable {
    private static final int INDEX_ID = 0;
    private static final int INDEX_DESCRIPTION = 1;
    private static final int INDEX_GTIN_UPC = 2;
    private static final String DELIMITER = "_";

    @Override
    public String toString() {
        return String.format("Food (id = %d) description : %s gtinUpc : %s", fdcId, description, gtinUpc);
    }

    public static Food parseFood(String text) {
        String[] args = text.split(DELIMITER);

        long id = Long.parseLong(args[INDEX_ID]);
        String description = args[INDEX_DESCRIPTION];
        String gtinUpc = args[INDEX_GTIN_UPC];

        return new Food(id, description, gtinUpc);
    }

    public String formatFood() {
        return String.join(DELIMITER,
                Long.toString(fdcId),
                description,
                gtinUpc);
    }
}
