package store.util;

import static store.util.constant.ErrorMessage.FAIL_FILE_UPDATE;
import static store.util.constant.ErrorMessage.FAIL_FILE_UPLOAD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static List<String> readFromFile(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(filePath))
        ) {
            addToken(data, reader);
        } catch (IOException e) {
            throw new IOException(FAIL_FILE_UPLOAD.getMessage());
        }
        return data;
    }


    public static void writeToFile(List<String> userInput, String filePath) throws IOException  {
        try (
                PrintWriter writer = new PrintWriter(filePath);
        )
        {
            for (String str : userInput) {
                writer.println(str);
            }
        } catch (FileNotFoundException e) {
            throw new IOException(FAIL_FILE_UPDATE.getMessage());
        }
    }

    private static void addToken(List<String> data, BufferedReader reader) throws IOException {
        String token;
        while ((token = reader.readLine()) != null) {
            if(!token.isBlank()) {
                data.add(token);
            }
        }
    }
}