package bg.sofia.uni.fmi.mjt.server;

import bg.sofia.uni.fmi.mjt.server.database.DatabaseManager;
import bg.sofia.uni.fmi.mjt.exceptions.UnsupportedCommandException;
import bg.sofia.uni.fmi.mjt.food.Food;
import bg.sofia.uni.fmi.mjt.food.FoodReport;
import bg.sofia.uni.fmi.mjt.server.command.Command;
import bg.sofia.uni.fmi.mjt.server.command.CommandExecutor;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.UNKNOWN_COMMAND;
import static bg.sofia.uni.fmi.mjt.server.command.CommandExecutor.formatResponseFoodReport;
import static bg.sofia.uni.fmi.mjt.server.command.CommandExecutor.formatResponseFoods;
import static bg.sofia.uni.fmi.mjt.server.command.CommandType.*;

public class Server {
    private static final int DEFAULT_SERVER_PORT = 8888;
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 1024;

    private ByteBuffer buffer;

    private DatabaseManager database;

    public Server() {
        this.database = new DatabaseManager();
        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            Selector selector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT));
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    // select() is blocking but may still return with 0, check javadoc
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();

                        buffer.clear();
                        int r = sc.read(buffer);
                        if (r < 0) {
                            System.out.println("Client has closed the connection");
                            sc.close();
                            continue;
                        }

                        Command clientCommand = Command.parse(buffer);

                        String result = execute(clientCommand);

                        sendRespondToClient(result, sc);

                        database.save();

                    } else if (key.isAcceptable()) {
                        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = sockChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    }

                    keyIterator.remove();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }

    private String execute(Command command) {
        String commandValue = command.getValue();

        boolean contains = switch (command.type()) {
            case GET_FOOD -> database.containsKeyword(commandValue);
            case GET_FOOD_BY_BARCODE -> database.containsFoodByBarcode(commandValue);
            case GET_FOOD_REPORT_BY_ID -> database.containsFoodReport(Long.parseLong(commandValue));
        };

        if (!contains) {
            saveToDatabase(command);
        }

        return getFromDatabase(command);
    }

    private void saveToDatabase(Command command) {
        String commandValue = command.getValue();

        if (command.type() == GET_FOOD) {
            List<Food> result = CommandExecutor.getFoodsByNameFromApi(commandValue);
            database.addFoodsByKeyword(commandValue, result);
        }

        if (command.type() == GET_FOOD_BY_BARCODE) {
            Food result = CommandExecutor.getFoodByBarcodeFromApi(commandValue);
            database.addFoodByBarcode(result);
        }

        if (command.type() == GET_FOOD_REPORT_BY_ID) {
            FoodReport result = CommandExecutor.getFoodReportByFdcIdFromApi(commandValue);
            database.addFoodReport(result);
        }
    }

    private String getFromDatabase(Command command) {
        String commandValue = command.getValue();

        if (command.type() == GET_FOOD) {
            List<Food> result = database.getFoodsByKeyword(commandValue);
            return formatResponseFoods(result);
        }

        if (command.type() == GET_FOOD_BY_BARCODE) {
            List<Food> result = List.of(database.getFoodByBarcode(commandValue));
            return formatResponseFoods(result);
        }

        if (command.type() == GET_FOOD_REPORT_BY_ID) {
            FoodReport result = database.getFoodReport(Long.parseLong(commandValue));
            return formatResponseFoodReport(result);
        }

        throw new UnsupportedCommandException(UNKNOWN_COMMAND + command.type());
    }

    private void sendRespondToClient(String respond, SocketChannel clientChannel) throws IOException {
        buffer.clear();

        buffer.put(respond.getBytes());

        buffer.flip();
        clientChannel.write(buffer);
    }

    private void printBufferContent() { // to-do delete it
        buffer.flip();

        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        String content = new String(clientInputBytes, StandardCharsets.UTF_8)
                .trim();

        System.out.println(content);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();

//        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
//            Selector selector = Selector.open();
//
//            serverSocketChannel.bind(new InetSocketAddress(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT));
//            serverSocketChannel.configureBlocking(false);
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
//
//            while (true) {
//                int readyChannels = selector.select();
//                if (readyChannels == 0) {
//                    // select() is blocking but may still return with 0, check javadoc
//                    continue;
//                }
//
//                Set<SelectionKey> selectedKeys = selector.selectedKeys();
//                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
//
//                while (keyIterator.hasNext()) {
//                    SelectionKey key = keyIterator.next();
//                    if (key.isReadable()) {
//                        SocketChannel sc = (SocketChannel) key.channel();
//
//                        buffer.clear();
//                        int r = sc.read(buffer);
//                        if (r < 0) {
//                            System.out.println("Client has closed the connection");
//                            sc.close();
//                            continue;
//                        }
//
//                        Command clientCommand = Command.parse(buffer);
//                        System.out.println(clientCommand);
//
//                        String result = CommandExecutor.execute(clientCommand);
//
//                        sendRespondToClient(result, sc, buffer);
//
//                    } else if (key.isAcceptable()) {
//                        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
//                        SocketChannel accept = sockChannel.accept();
//                        accept.configureBlocking(false);
//                        accept.register(selector, SelectionKey.OP_READ);
//                    }
//
//                    keyIterator.remove();
//                }
//
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException("There is a problem with the server socket", e);
//        }
    }
}
