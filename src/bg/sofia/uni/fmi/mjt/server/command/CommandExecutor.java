package bg.sofia.uni.fmi.mjt.server.command;

import bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.food.Food;
import bg.sofia.uni.fmi.mjt.food.FoodReport;
import bg.sofia.uni.fmi.mjt.food_api.requests.HttpRequestFactory;
import bg.sofia.uni.fmi.mjt.food_api.responds.RespondFoods;

import com.google.gson.Gson;

import java.io.IOException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;

public class CommandExecutor { // to-do refactor class
    private static final int MAX_RESULT_SIZE = 10;
    private static final String DELIMINATOR = ";";

    public static List<Food> getFoodsByNameFromApi(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessages.PRODUCT_NAME_NULL_OR_EMPTY);
        }

        HttpRequest requestForFoodByKeyword = HttpRequestFactory.createRequestForFoodsByName(name);
        RespondFoods respond = getFoodRespondFromApi(requestForFoodByKeyword);

        return respond.foods();
    }

    public static Food getFoodByBarcodeFromApi(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessages.PRODUCT_GTIN_UPC_CODE_NULL_OR_EMPTY);
        }

        HttpRequest requestForFoodByKeyword = HttpRequestFactory.createRequestForFoodByGtinUpcCode(code);
        RespondFoods respond = getFoodRespondFromApi(requestForFoodByKeyword);

        return respond.foods().getFirst();
    }

    public static FoodReport getFoodReportByFdcIdFromApi(String fdcId) {
        if (fdcId == null || fdcId.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessages.PRODUCT_ID_NULL_OR_EMPTY);
        }

        HttpRequest requestByFcdId = HttpRequestFactory.createRequestForFoodByFcdId(fdcId);
        return getFoodReportRespondFromApi(requestByFcdId);
    }

    public static String formatResponseFoodReport(FoodReport report) {
        return String.format("%s%n", report.formatFoodReport());
    }

    public static String formatResponseFoods(List<Food> foods) {
        List<String> foodList = foods
                .stream()
                .limit(MAX_RESULT_SIZE)
                .map(Food::formatFood)
                .toList();

        String result = String.join(DELIMINATOR, foodList);

        return String.format("%s%n", result);
    }

    private static RespondFoods getFoodRespondFromApi(HttpRequest request) {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), RespondFoods.class);
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Make custom exception"); // to-do make custom exception
        }
    }

    private static FoodReport getFoodReportRespondFromApi(HttpRequest request) {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            return gson.fromJson(response.body(), FoodReport.class);
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Make custom exception"); // to-do make custom exception
        }
    }

    private CommandExecutor() {
        //don't want instances of this class
    }
}
