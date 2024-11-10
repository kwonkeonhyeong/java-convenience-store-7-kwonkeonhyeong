package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

public class Get {

    private static final String INVALID_MIN_GET = "[ERROR] Get은 0 이상이어야 합니다.";

    private static final int MIN_GET = 0;

    private final Long get;

    private Get(Long get) {
        validate(get);
        this.get = get;
    }

    public static Get valueOf(String input) {
        String stripped = input.strip();
        validateEmpty(stripped);
        validateNumeric(stripped);
        return new Get(Long.parseLong(stripped));
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
        if (quantity < MIN_GET) {
            throw new IllegalArgumentException(INVALID_MIN_GET);
        }
    }

    public Long getGet() {
        return get;
    }
}
