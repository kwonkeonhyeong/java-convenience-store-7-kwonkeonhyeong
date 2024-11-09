package store.domain;

import store.domain.vo.Price;

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

    public Long calculateDeficitQuantity() {
        Long promotionStock = stock.getQuantity();
        Long promotionQuantity = promotion.calculatePromotionQuantity(checkPromotionGiftQuantity());
        return promotion.calculateNonPromotionalQuantity(order,stock) - (promotionStock - promotionQuantity);
    }

    public Long calculatePurchaseAmount() {
        return stock.calculateAmount(order.getQuantity());
    }

    public Long calculatePromotionQuantityAmount() {
        if(hasPromotion()) {
            Long promotionQuantity = promotion.calculatePromotionQuantity(checkPromotionGiftQuantity());
            return stock.calculateAmount(promotionQuantity);
        }
        return 0L;
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
        String format = "%-10s %-5s %-10s\n";
        Price price = stock.getPrice();
        String formattedPrice = String.format("%,d", price.getPrice());
        return String.format(format, order.getName(), order.getQuantity(), formattedPrice);
    }

    public String formatPromotion() {
        String format = "%-10s %-5s\n";
        return String.format(format, order.getName(), checkPromotionGiftQuantity());
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
