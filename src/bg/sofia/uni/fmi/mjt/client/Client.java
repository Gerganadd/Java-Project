package bg.sofia.uni.fmi.mjt.client;

import bg.sofia.uni.fmi.mjt.food.Food;
import bg.sofia.uni.fmi.mjt.food.FoodReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static bg.sofia.uni.fmi.mjt.client.CommandType.DISCONNECT;
import static bg.sofia.uni.fmi.mjt.client.CommandType.UNKNOWN;

public class Client {
    private static final String DELIMITER = ";";
    private static final String MENU = """
            Commands:
            help
            disconnect
            get-food <name>
            get-food-report <fsdId>
            get-food-by-barcode --img=<absoluteImagePath>
            get-food-by-barcode --code=<gtin/upcCode>
            get-food-by-barcode --img=<absoluteImagePath> --code=<gtin/upcCode>
            get-food-by-barcode --code=<gtin/upcCode> --img=<absoluteImagePath>
            """;

    private static final String MENU_OPTIONS_DESCRIPTION = """
            disconnect -> disconnect you from the server
            get-food <name> -> return information about all foods which match of given name
            get-food-report <fsdId> -> return information about food with given id (fsdId is specific for each food)
            get-food-by-barcode --code=<gtin/upcCode> -> return information about food with given gtin/upc code
            get-food-by-barcode --img=<absoluteImagePath> -> return information about food with given gtin/upc code from image
            get-food-by-barcode --img=<absoluteImagePath> --code=<gtin/upcCode> -> same as the above two but will prioritize the code value
            """;
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, "UTF-8"));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, "UTF-8"), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress("localhost", SERVER_PORT));

            System.out.println("Connected to the server.");
            printMenu();

            while (true) {
                System.out.print("Enter command: ");
                String message = scanner.nextLine(); // read a line from the console

                CommandType command = CommandParser.parse(message);

                while (command == UNKNOWN) {
                    System.out.println("Unknown command! Please insert correct command.");
                    //printMenu();

                    message = scanner.nextLine();
                    command = CommandParser.parse(message);

                    if (command.getText().equals("help")) {
                        printMenuDescription();

                        message = scanner.nextLine();
                        command = CommandParser.parse(message);
                    }
                }

                if (command == DISCONNECT) {
                    // to-do ?
                    System.out.println("Disconnected from the server");
                    break;
                }

                System.out.println("Sending message <" + command + " " + message + "> to the server...");

                writer.println(message);

                String reply = reader.readLine(); // read the response from the server

                switch (command) {
                    case GET_FOOD_BY_NAME -> printFoodsFromResult(reply);
                    case GET_FOOD_REPORT_BY_FCD_ID -> printFoodReportFromResult(reply);
                    case GET_FOOD_BY_BARCODE -> printFoodsFromResult(reply); // may change it
                }

                //System.out.println("The server replied <" + reply + ">");

            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private static void printFoodReportFromResult(String reply) {
        String[] elements = reply.split(DELIMITER);

        for (String text : elements) {
            FoodReport current = FoodReport.parseFoodReport(text);
            System.out.println(current);
        }
    }

    private static void printFoodsFromResult(String reply) {
        String[] foods = reply.split(DELIMITER);

        for (String text : foods) {
            Food current = Food.parseFood(text);
            System.out.println(current);
        }
    }

    private static void printMenu() { //? may move it
        System.out.println(MENU);
    }

    private static void printMenuDescription() {
        System.out.println(MENU_OPTIONS_DESCRIPTION);
    }
}
