package store.domain;

import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.Quantity;

public class BillingItem {
    private final ProductName productName;
    private final Price price;
    private final Quantity orderQuantity;
    private final Quantity promotionGiftQuantity;

    private BillingItem(ProductName productName, Price price, Quantity orderQuantity, Quantity promotionGiftQuantity) {
        this.productName = productName;
        this.price = price;
        this.orderQuantity = orderQuantity;
        this.promotionGiftQuantity = promotionGiftQuantity;
    }

    public static BillingItem of(Order order, Stock stock, Promotion promotion) {
        Quantity orderQuantity = Quantity.valueOf(order.getQuantity());
        Quantity promotionGiftQuantity = checkPromotionGiftQuantity(order,stock, promotion);
        return new BillingItem(stock.getProductName(), stock.getPrice(), orderQuantity, promotionGiftQuantity);
    }

    private static Quantity checkPromotionGiftQuantity(Order order, Stock stock, Promotion promotion) {
        if (promotion != null) {
            Long quantity = promotion.calculatePromotionGiftQuantity(order, stock);
            return Quantity.valueOf(quantity);
        }
        return Quantity.valueOf(0L);
    }

    @Override
    public String toString() {
        return "BillingItem{" +
                "productName=" + productName +
                ", price=" + price +
                ", orderQuantity=" + orderQuantity +
                ", promotionGiftQuantity=" + promotionGiftQuantity +
                '}';
    }
}
