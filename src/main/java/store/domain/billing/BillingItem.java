package store.domain.billing;

import static store.domain.constant.BillPattern.ITEM_FORMAT;

import store.domain.stock.Promotion;
import store.domain.stock.Stock;
import store.domain.order.Order;

public class BillingItem {

    private static final Long NON_PROMOTION_QUANTITY = 0L;
    private static final Long NON_PROMOTION_AMOUNT = 0L;

    private final Order order;
    private final Stock stock;
    private final Promotion promotion;

    private BillingItem(Order order, Stock stock, Promotion promotion) {
        this.order = order;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static BillingItem of(Order order, Stock stock, Promotion promotion) {
        return new BillingItem(order, stock, promotion);
    }

    public Long checkPromotionGiftQuantity() {
        if (promotion != null) {
            return promotion.calculatePromotionGiftQuantity(order, stock);
        }
        return NON_PROMOTION_QUANTITY;
    }

    public Long calculateDeficitQuantity() {
        Long promotionStock = stock.getQuantity();
        Long promotionQuantity = promotion.calculatePromotionQuantity(checkPromotionGiftQuantity());
        return promotion.calculateNonPromotionalQuantity(order, stock) - (promotionStock - promotionQuantity);
    }

    public Long calculatePurchaseAmount() {
        return stock.calculateAmount(order.getQuantity());
    }

    public Long calculatePromotionAmount() {
        if (hasPromotion()) {
            Long promotionQuantity = promotion.calculatePromotionQuantity(checkPromotionGiftQuantity());
            return stock.calculateAmount(promotionQuantity);
        }
        return NON_PROMOTION_AMOUNT;
    }

    public Long calculatePromotionDiscountAmount() {
        return stock.calculateAmount(checkPromotionGiftQuantity());
    }

    public boolean hasNoStock() {
        return stock.getQuantity() < order.getQuantity();
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public String formatOrder() {
        return ITEM_FORMAT.formatOrderItem(
                order.getName(),
                order.getQuantity(),
                stock.calculateAmount(order.getQuantity())
        );
    }

    public String formatPromotion() {
        return ITEM_FORMAT.formatOrderItem(
                order.getName(),
                checkPromotionGiftQuantity(),
                null
        );
    }

    public String getOrderProductName() {
        return order.getName();
    }

    public Long getOrderQuantity() {
        return order.getQuantity();
    }

    public Long getStockQuantity() {
        return stock.getQuantity();
    }

}
