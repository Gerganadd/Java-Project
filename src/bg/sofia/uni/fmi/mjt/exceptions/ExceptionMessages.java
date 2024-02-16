package bg.sofia.uni.fmi.mjt.exceptions;

public class ExceptionMessages {
    private ExceptionMessages() {
        //don't want instances of this class
    }

    public static final String FILE_NAME_NULL_OR_EMPTY = "File name can't be null or empty";
    public static final String PRODUCT_NAME_NULL_OR_EMPTY = "Product name can't be null or empty";
    public static final String PRODUCT_ID_NULL_OR_EMPTY = "Product id can't be null or empty";
    public static final String PRODUCT_GTIN_UPC_CODE_NULL_OR_EMPTY = "Gtin/upc code can't be null or empty";
    public static final String NUTRIENT_VALUE_NULL_OR_EMPTY = "Nutrient value can't be null or empty";
    public static final String NUTRIENT_REPORT_NULL_OR_EMPTY = "Nutrient report can't be null or empty";
    public static final String FOOD_REPORT_NULL_OR_EMPTY = "Nutrient report can't be null or empty";

    public static final String KEY_DOES_NOT_CONTAINS_IN_DATABASE = "Database doesn't contains key : ";
    public static final String FDC_ID_DOES_NOT_CONTAINS_IN_DATABASE = "Database doesn't contains food report by id : "; //?
    public static final String KEYWORD_DOES_NOT_CONTAINS_IN_DATABASE = "Database doesn't contains keyword : "; //?
    public static final String GTIN_UPC_CODE_DOES_NOT_CONTAINS_IN_DATABASE = "Database doesn't contains food by gtin/upc code : "; //?

    public static final String UNKNOWN_COMMAND = "Unknown command type : ";

    public static final String ONLY_DIGITS = "Arguments of the command must contains only digits";
    public static final String ONLY_LETTERS = "Arguments of the command must contains only letters";

}
