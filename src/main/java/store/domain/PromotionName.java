package store.domain;

public class PromotionName {
    private final String promotionName;

    private PromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public static PromotionName valueOf(String input) {
        if (input.equals("null")) {
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
