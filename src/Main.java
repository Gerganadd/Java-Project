import bg.sofia.uni.fmi.mjt.exceptions.ExceptionFileManager;
import bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages;
import bg.sofia.uni.fmi.mjt.exceptions.NoSuchElementException;
import bg.sofia.uni.fmi.mjt.exceptions.Status;

import java.io.IOException;

public class Main {
    private static final String API_KEY = "2vPhFALHihIOLVYt0VjFvRJJjpFRsnFGR38NoM8K";
    public static void main(String[] args) throws IOException, InterruptedException {

        Exception e1 = new IllegalArgumentException(ExceptionMessages.FILE_NAME_NULL_OR_EMPTY);
        Exception e2 = new IndexOutOfBoundsException(ExceptionMessages.NUTRIENT_VALUE_NULL_OR_EMPTY);
        Exception e3 = new NoSuchElementException(ExceptionMessages.NUTRIENT_REPORT_NULL_OR_EMPTY);

        ExceptionFileManager file = new ExceptionFileManager();
        file.addNewException(e1, Status.FROM_CLIENT);
        file.addNewException(e3, Status.FROM_SERVER);
        file.addNewException(e2, Status.NOT_FOUND_ELEMENT);

        file.save();

//        Map<Long, Food> foods = new HashMap<>();
//        foods.put(200012L, new Food(200012, "Tomatoes", "Bivolsko syrce"));
//        foods.put(200013L, new Food(200013, "Cucumber", "Gergana"));
//
//        FileManager manager = new FoodFileManager();
//        manager.save(foods);
//
//        Map<Long, Food> foodContent = manager.load();
//        foodContent.forEach((x, y)-> System.out.println(x + ": " + y));
//
//        while (true) {
//            System.out.println("*");
//        }


//        String siteUrl = "https://api.nal.usda.gov/fdc/";
//        HttpClient client = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_2)
//                .build();

//        String productId = "415269";
//        String httpsGetProductById = String.format("%sv1/food/%s?api_key=%s"
//                , siteUrl, productId, API_KEY);
//        HttpRequest request3 = HttpRequest.newBuilder()
//                .uri(URI.create(httpsGetProductById))
//                .build(); // equivalent
//
//        HttpResponse<String> response3 =  client.send(request3, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response3.body());
        //System.out.println(httpsGetProductById);

        //HttpRequest requestByFcdId = Request.createRequestForProductByFcdId("415269");
        //HttpRequest requestByGtinUpcCode = Request.createRequestForProductByGtinUpcCode("009800146130");
//        HttpRequest requestByProductName = Request.createRequestForProductByName("raffaello treat");
//
//        //HttpResponse<String> response =  client.send(requestByFcdId, HttpResponse.BodyHandlers.ofString());
//        //HttpResponse<String> response =  client.send(requestByGtinUpcCode, HttpResponse.BodyHandlers.ofString());
//        HttpResponse<String> response =  client.send(requestByProductName, HttpResponse.BodyHandlers.ofString());
//
//        Gson gson = new Gson();
//        Respond res = gson.fromJson(response.body(), Respond.class);
//        System.out.println(res);

//
//        String productName = "raffaello%20treat";
//        String httpsGetProductRequest =
//                String.format("%sv1/foods/search?query=%s&requireAllWords=true&api_key=%s",
//                        siteUrl, productName, API_KEY);
//
//        System.out.println(httpsGetProductRequest);



        //test parsing Nutrients from json
        //String text = "{\"nutrientId\":1004,\"nutrientName\":\"Total lipid (fat)\",\"nutrientNumber\":\"204\",\"unitName\":\"G\",\"derivationCode\":\"LCCS\",\"derivationDescription\":\"Calculated from value per serving size measure\",\"derivationId\":70,\"value\":50.0,\"foodNutrientSourceId\":9,\"foodNutrientSourceCode\":\"12\",\"foodNutrientSourceDescription\":\"Manufacturer's analytical; partial documentation\",\"rank\":800,\"indentLevel\":1,\"foodNutrientId\":25532011,\"percentDailyValue\":22}";
        //JsonNutrient nutrient = gson.fromJson(text, JsonNutrient.class);
        //System.out.println(nutrient);

        //Nutrient normalNutrient = gson.fromJson(text, Nutrient.class);
        //System.out.println(normalNutrient);

//        String siteUrl = "https://api.nal.usda.gov/fdc/";
//        HttpClient client = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_2)
//                .build();
//
//        String productGtinUpc = "009800146130";
//        String httpsGetProductByGtinUpc =
//                String.format("%sv1/foods/search?query=%s&requireAllWords=true&api_key=%s",
//                        siteUrl, productGtinUpc, API_KEY);
//        HttpRequest request4 = HttpRequest.newBuilder()
//                .uri(URI.create(httpsGetProductByGtinUpc))
//                .build(); // equivalent
//
//        HttpResponse<String> response4 =  client.send(request4, HttpResponse.BodyHandlers.ofString());
//
//        //Type type = new TypeToken<List<Product>>(){}.getType();
//        Respond respond = gson.fromJson(response4.body(), Respond.class);
//        System.out.print(respond);
//        System.out.print(respond);

        //test json parsing
        /*
        Product product = new Product("Something important", "234432");
        Gson gson = new Gson();
        String json = gson.toJson(product);
        // {"description":"Something important","id":"234432"}
        System.out.println(json);

        Product product2 = gson.fromJson(json, Product.class);
        System.out.println(product2);

        List<Developer> devs = gson.fromJson(reader, List.class);
         */

        // test connection to web
//        String siteUrl = "https://api.nal.usda.gov/fdc/";
//        HttpClient client = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_2)
//                .build();
        /*
        String productName = "raffaello%20treat";
        String httpsGetProductRequest =
                String.format("%sv1/foods/search?query=%s&requireAllWords=true&api_key=%s",
                        siteUrl, productName, API_KEY);

        //https://api.nal.usda.gov/fdc/v1/foods/search?query=raffaello%20treat&requireAllWords=true&api_key=API_KEY

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(httpsGetProductRequest))
                .build(); // equivalent

        HttpResponse<String> response2 =  client.send(request2, HttpResponse.BodyHandlers.ofString());

        //System.out.println(request2.toString());
        System.out.println(response2.body());


        String productId = "2041155";
        String httpsGetProductById = String.format("%sv1/foods/{%s}api_key=%s", siteUrl, productId, API_KEY);
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(URI.create(httpsGetProductRequest))
                .build(); // equivalent

        HttpResponse<String> response3 =  client.send(request3, HttpResponse.BodyHandlers.ofString());

        System.out.println(response3.body());
        //System.out.println(response2.body().equals(response3.body()));
        */
        /*
        String productGtinUpc = "009800146130";
        String httpsGetProductByGtinUpc =
                String.format("%sv1/foods/search?query=%s&requireAllWords=true&api_key=%s",
                        siteUrl, productGtinUpc, API_KEY);
        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(URI.create(httpsGetProductByGtinUpc))
                .build(); // equivalent

        HttpResponse<String> response4 =  client.send(request4, HttpResponse.BodyHandlers.ofString());

        System.out.println(response4.body());
        //System.out.println(response4.body().equals(response3.body()));
        */

    }
}