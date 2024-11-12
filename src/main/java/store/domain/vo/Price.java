package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

import java.util.Objects;

public class Price {

    private static final String INVALID_MIN_PRICE = "[ERROR] 가격은 0 이상이어야 합니다.";

    private static final int MIN_PRICE = 0;

    private final Long price;

    private Price(Long price) {
        validate(price);
        this.price = price;
    }

    public static Price valueOf(String input) {
        String stripped = input.strip();
        validateEmpty(stripped);
        validateNumeric(stripped);
        return new Price(Long.parseLong(stripped));
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

    private void validate(Long quantity) {
        validateMixQuantity(quantity);
    }

    private void validateMixQuantity(Long quantity) {
        if (quantity < MIN_PRICE) {
            throw new IllegalArgumentException(INVALID_MIN_PRICE);
        }
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price price1)) {
            return false;
        }
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }

    @Override
    public String toString() {
        return String.valueOf(price);
    }
}
