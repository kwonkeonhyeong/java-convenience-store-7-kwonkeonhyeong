package store;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
    @Test
    void 파일을_업로드할_수_없는_경우_예외처리() {
        FileHandler fileHandler = new FileHandler();
        assertThatThrownBy(() -> fileHandler.readFromFile("wrongPath"))
                .isInstanceOf(IOException.class)
                .hasMessage("[ERROR] 파일을 업로드 할 수 없습니다.");
    }
}