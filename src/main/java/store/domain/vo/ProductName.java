package store.domain.vo;

import java.util.Objects;

public class ProductName {

    private final String name;

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName valueOf(String input) {
        String stripped = input.strip();
        validateEmpty(stripped);
        return new ProductName(stripped);
    }

    private static void validateEmpty(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductName that)) {
            return false;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
