package bg.sofia.uni.fmi.mjt.server.database;

import bg.sofia.uni.fmi.mjt.exceptions.NoSuchElementException;
import bg.sofia.uni.fmi.mjt.food.Food;
import bg.sofia.uni.fmi.mjt.food.FoodReport;

import java.util.List;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.PRODUCT_GTIN_UPC_CODE_NULL_OR_EMPTY;
import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.PRODUCT_NAME_NULL_OR_EMPTY;
import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.GTIN_UPC_CODE_DOES_NOT_CONTAINS_IN_DATABASE;
import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.KEYWORD_DOES_NOT_CONTAINS_IN_DATABASE;
import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.FDC_ID_DOES_NOT_CONTAINS_IN_DATABASE;

public class DatabaseManager {
    private static final String DEFAULT_FOOD_REPORTS_FILE_NAME = "food_reports_data.json";
    private static final String DEFAULT_FOODS_BY_KEYWORD_FILE_NAME = "food_by_keywords_data.json";
    private static final String DEFAULT_FOOD_BY_BARCODE_FILE_NAME = "food_by_barcodes_data.json";

    private String foodReportsFileName;
    private String foodsByKeywordFileName;
    private String foodsByBarcodeFileName;

    private DatabaseOperations<Long, FoodReport> foodReportsDatabase;
    private DatabaseOperations<String, Food> foodsByKeywordDatabase;
    private DatabaseOperations<String, Food> foodsByBarcodeDatabase;

    public DatabaseManager() {
        this(DEFAULT_FOOD_REPORTS_FILE_NAME, DEFAULT_FOODS_BY_KEYWORD_FILE_NAME,
                DEFAULT_FOOD_BY_BARCODE_FILE_NAME);
    }

    public DatabaseManager(String foodReportsFileName, String foodsByKeywordFileName,
                           String foodsByBarcodeFileName) {
        validateFileName(foodReportsFileName);
        validateFileName(foodsByKeywordFileName);
        validateFileName(foodsByBarcodeFileName);

        this.foodReportsFileName = foodReportsFileName;
        this.foodsByKeywordFileName = foodsByKeywordFileName;
        this.foodsByBarcodeFileName = foodsByBarcodeFileName;

        this.foodReportsDatabase = new Database<>();
        this.foodsByKeywordDatabase = new Database<>();
        this.foodsByBarcodeDatabase = new Database<>();
    }

    public void save() {
        foodReportsDatabase.saveTo(foodReportsFileName);
        foodsByKeywordDatabase.saveTo(foodsByKeywordFileName);
        foodsByBarcodeDatabase.saveTo(foodsByBarcodeFileName);
    }

    public void load() {
        foodReportsDatabase.loadFrom(foodReportsFileName);
        foodsByKeywordDatabase.loadFrom(foodsByKeywordFileName);
        foodsByBarcodeDatabase.loadFrom(foodsByBarcodeFileName);
    }

    public void addFoodReport(FoodReport report) {
        if (report == null) {
            return; // or throw exception
        }

        foodReportsDatabase.add(report.fdcId(), report);
    }

    public boolean containsFoodReport(long fdcId) {
        return foodReportsDatabase.contains(fdcId);
    }

    public FoodReport getFoodReport(long fcdId) {
        if (!foodReportsDatabase.contains(fcdId)) {
            throw new NoSuchElementException(FDC_ID_DOES_NOT_CONTAINS_IN_DATABASE + fcdId);
        }

        return foodReportsDatabase.get(fcdId);
    }

    public void addFoodsByKeyword(String keyword, List<Food> foods) {
        if (keyword == null || keyword.isBlank() || foods.isEmpty()) {
            return; // or throw exception
        }

        foodsByKeywordDatabase.addAll(keyword, foods);

        addAllFoodsWithBarcodes(foods);
    }

    public boolean containsKeyword(String keyword) {
        return foodsByKeywordDatabase.contains(keyword);
    }

    public List<Food> getFoodsByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(PRODUCT_NAME_NULL_OR_EMPTY);
        }
        if (!foodsByKeywordDatabase.contains(keyword)) {
            throw new NoSuchElementException(KEYWORD_DOES_NOT_CONTAINS_IN_DATABASE + keyword);
        }

        return foodsByKeywordDatabase.getAll(keyword).stream().toList();
    }

    public void addFoodByBarcode(Food food) {
        if (food == null || food.gtinUpc() == null) {
            return; // or throw exception
        }

        foodsByBarcodeDatabase.add(food.gtinUpc(), food);
    }

    public boolean containsFoodByBarcode(String code) {
        return foodsByBarcodeDatabase.contains(code);
    }

    public Food getFoodByBarcode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(PRODUCT_GTIN_UPC_CODE_NULL_OR_EMPTY);
        }
        if (!foodsByBarcodeDatabase.contains(code)) {
            throw new NoSuchElementException(GTIN_UPC_CODE_DOES_NOT_CONTAINS_IN_DATABASE + code);
        }

        return foodsByBarcodeDatabase.get(code);
    }

    private void addAllFoodsWithBarcodes(List<Food> foods) {
        foods
                .stream()
                .filter(x -> x.gtinUpc() != null)
                .forEach(x -> foodsByBarcodeDatabase.add(x.gtinUpc(), x));
    }

    private void validateFileName(String fileName) {
        // to-do
    }
}
