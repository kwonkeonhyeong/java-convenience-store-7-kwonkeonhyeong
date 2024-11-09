package store.domain;

public class BillingItem {

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
        return 0L;
    }

    public Long checkNormalPaymentQuantity() {
        if (promotion != null) {
            Long quantity = promotion.calculatePromotionGiftQuantity(order, stock);
            return order.getQuantity() - promotion.calculatePromotionQuantity(quantity);
        }
        return order.getQuantity();
    }

    public Long calculateDeficitQuantity() {
        Long promotionStock = stock.getQuantity();
        Long promotionQuantity = promotion.calculatePromotionQuantity(checkPromotionGiftQuantity());
        return promotion.calculateNonPromotionalQuantity(order,stock) - (promotionStock - promotionQuantity);
    }

    public boolean hasNoStock() {
        return stock.getQuantity() <= order.getQuantity();
    }

    public boolean hasPromotion() {
        return promotion != null;
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
