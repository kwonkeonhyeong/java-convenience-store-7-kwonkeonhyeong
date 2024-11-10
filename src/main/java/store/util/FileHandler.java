package store.util;

import static store.util.constant.ErrorMessage.FAIL_FILE_UPLOAD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
* 파일 입출력을 관리하는 역할을 부여
* */
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

    private static void addToken(List<String> data, BufferedReader reader) throws IOException {
        String token;
        while ((token = reader.readLine()) != null) {
            if (!token.isBlank()) {
                data.add(token);
            }
        }
    }
}
