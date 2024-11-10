package store.domain.vo;
/*
* 프로모션 이름은 변하지 않는 값 이므로 vo로 생성
* */
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
