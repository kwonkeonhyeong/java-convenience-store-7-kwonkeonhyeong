package store.domain.vo;

import java.util.Objects;

public class Quantity {

    private final Long quantity;

    private Quantity(Long quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    public static Quantity valueOf(String input) {
        String stripped = input.strip();
        validateEmpty(stripped);
        validateNumeric(stripped);
        return new Quantity(Long.parseLong(stripped));
    }

    private static void validateEmpty(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void validateNumeric(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    private void validate(Long quantity) {
        validateMixQuantity(quantity);
    }

    private void validateMixQuantity(Long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public Quantity add() {
        return new Quantity(quantity+1);
    }

    public Quantity decrease(Long quantity) {
        return new Quantity(this.quantity-quantity);
    }

    public Long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity that)) {
            return false;
        }
        return Objects.equals(quantity, that.quantity);
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
