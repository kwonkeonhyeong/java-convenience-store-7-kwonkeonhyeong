package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

public class PromotionName {

    private static final String NON_PROMOTION_DEFAULT_DATA = "null";

    private final String promotionName;

    private PromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public static PromotionName valueOf(String input) {
        String stripped = input.strip();
        validateEmpty(stripped);
        if (input.equals(NON_PROMOTION_DEFAULT_DATA)) {
            return null;
        }
        return new PromotionName(input);
    }

    private static void validateEmpty(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    public String getPromotionName() {
        return promotionName;
    }

    @Override
    public String toString() {
        return promotionName;
    }

}
