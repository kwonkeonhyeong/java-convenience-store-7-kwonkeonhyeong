package store.domain.constant;

import static store.domain.constant.AmountPattern.AMOUNT_UNIT;

import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;

public enum StockPattern {

    STOCK_FORMAT("- %s %s원 %s %s");

    private static final String OUT_OF_STOCK_MESSAGE = "재고 없음";
    private static final String QUANTITY_PATTERN = "%s개";
    private static final String NON_PROMOTION_NAME = "";

    private final String pattern;

    StockPattern(String pattern) {
        this.pattern = pattern;
    }

    public String formatStock(ProductName name, Price price, Long quantity, PromotionName promotion) {
        String formattedPrice = String.format(AMOUNT_UNIT.getPattern(), price.getPrice());
        String formattedQuantity = String.format(QUANTITY_PATTERN, quantity);
        String formattedPromotionName = NON_PROMOTION_NAME;
        if (promotion != null) {
            formattedPromotionName = promotion.getPromotionName();
        }
        if (quantity == 0L) {
            return String.format(pattern, name, formattedPrice, OUT_OF_STOCK_MESSAGE, formattedPromotionName);
        }
        return String.format(pattern, name, formattedPrice, formattedQuantity, formattedPromotionName);
    }

}
