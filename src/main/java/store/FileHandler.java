package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public List<String> readFromFile(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(filePath))
        ) {
            String token;
            if((token = reader.readLine()) != null) {
                data.add(token);
            }
        } catch (IOException e) {
            throw new IOException("[Error] 파일을 업로드할 수 없습니다.");
        }
        return data;
    }
}
