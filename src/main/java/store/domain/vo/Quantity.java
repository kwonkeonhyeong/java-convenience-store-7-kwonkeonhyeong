package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

import java.util.Objects;
/*
* Quantity는 변하지만 Quantity내 값을 직접적으로 변경할 수 는 없도록 하여 vo로 구현
* */
public class Quantity {

    private static final String INVALID_MIN_QUANTITY = "[ERROR] 수량은 0 이상이어야 합니다.";

    private static final int MIN_QUANTITY = 0;
    private static final int INCREMENT_QUANTITY = 1;

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
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    private static void validateNumeric(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    public Quantity add() {
        return new Quantity(quantity + INCREMENT_QUANTITY);
    }

    public Quantity decrease(Long quantity) {
        return new Quantity(this.quantity - quantity);
    }

    private void validate(Long quantity) {
        validateMixQuantity(quantity);
    }

    private void validateMixQuantity(Long quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException(INVALID_MIN_QUANTITY);
        }
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
