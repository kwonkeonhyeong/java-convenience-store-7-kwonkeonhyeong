package store.domain.stock.vo;

import java.util.Objects;

public class Quantity {

    private final Long quantity;

    private Quantity(Long quantity) {
        this.quantity = quantity;
    }

    public static Quantity valueOf(Long quantity) {
        return new Quantity(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity quantity1)) {
            return false;
        }
        return Objects.equals(quantity, quantity1.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quantity);
    }

    @Override
    public String toString() {
        return String.valueOf(quantity);
    }
}
