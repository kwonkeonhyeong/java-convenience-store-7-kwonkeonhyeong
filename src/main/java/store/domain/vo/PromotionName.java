package store.domain.vo;

public class PromotionName {

    private static final String NON_PROMOTION_DEFAULT_DATA = "null";

    private final String promotionName;

    private PromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public static PromotionName valueOf(String input) {
        if (input.equals(NON_PROMOTION_DEFAULT_DATA)) {
            return null;
        }
        return new PromotionName(input);
    }

    public String getPromotionName() {
        return promotionName;
    }

    @Override
    public String toString() {
        return promotionName;
    }

}
