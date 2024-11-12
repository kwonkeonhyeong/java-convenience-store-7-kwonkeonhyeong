package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

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
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    public boolean matchesName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
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
