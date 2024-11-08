package store.domain.promotion;

public class PromotionName {
    private String promotionName;

    private PromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public static PromotionName valueOf(String input) {
        if(input.equals("null")) {
            return null;
        }
        return new PromotionName(input);
    }

    @Override
    public String toString() {
        return promotionName;
    }
}
