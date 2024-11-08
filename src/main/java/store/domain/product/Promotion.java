package store.domain.product;

public class Promotion {
    private final String promotion;

    private Promotion(String promotion) {
        this.promotion = promotion;
    }

    public static Promotion valueOf(String promotion) {
        if(promotion.equals("null")) {
            return null;
        }
        return new Promotion(promotion);
    }

    @Override
    public String toString() {
        return promotion;
    }
}
