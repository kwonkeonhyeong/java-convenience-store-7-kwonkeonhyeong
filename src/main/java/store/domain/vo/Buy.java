package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

public class Buy {

    private static final String INVALID_MIN_GET = "[ERROR] Buy은 0 이상이어야 합니다.";

    private static final int MIN_BUY = 0;

    private final Long buy;

    private Buy(Long buy) {
        validate(buy);
        this.buy = buy;
    }

    public static Buy valueOf(String input) {
        String stripped = input.strip();
        validateEmpty(stripped);
        validateNumeric(stripped);
        return new Buy(Long.parseLong(stripped));
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

    private void validate(Long buy) {
        validateMixBuy(buy);
    }

    private void validateMixBuy(Long quantity) {
        if (quantity < MIN_BUY) {
            throw new IllegalArgumentException(INVALID_MIN_GET);
        }
    }

    public Long getBuy() {
        return buy;
    }
}
