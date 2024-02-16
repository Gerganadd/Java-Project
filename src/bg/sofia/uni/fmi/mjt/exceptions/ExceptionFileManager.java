package bg.sofia.uni.fmi.mjt.exceptions;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.FILE_NAME_NULL_OR_EMPTY;

public class ExceptionFileManager {
    private static final String DEFAULT_FILE_NAME = "exceptions_data.json";
    private Gson gson;
    private String fileName;
    private List<String> exceptions;

    public ExceptionFileManager() {
        this(DEFAULT_FILE_NAME);
    }

    public ExceptionFileManager(String fileName) {
        validateFileName(fileName);
        this.fileName = fileName;
        this.gson = new Gson();
        this.exceptions = new ArrayList<>();
    }

    public void addNewException(Exception exception, Status status) {
        exceptions.add(formatExceptionInfo(status, exception));
    }

    public void save() {
        String information = gson.toJson(exceptions);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(information);
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e); // to-do create custom exception
        }
    }

    private String formatExceptionInfo(Status status, Exception exception) {
        return String.format("[%s] %s", status, exception.getMessage());
    }

    private void validateFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException(FILE_NAME_NULL_OR_EMPTY);
        }
    }
}
