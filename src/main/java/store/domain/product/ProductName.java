package store.domain.product;

import java.util.Objects;

public class ProductName {

    private final String name;

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName valueOf(String name) {
        return new ProductName(name);
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
