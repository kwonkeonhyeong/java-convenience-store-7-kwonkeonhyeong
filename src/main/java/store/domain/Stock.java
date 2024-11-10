package store.domain;

import static store.domain.constant.ProductInputIndex.NAME_INDEX;
import static store.domain.constant.ProductInputIndex.PRICE_INDEX;
import static store.domain.constant.ProductInputIndex.PROMOTION_INDEX;
import static store.domain.constant.ProductInputIndex.QUANTITY_INDEX;
import static store.domain.constant.StockPattern.STOCK_FORMAT;
import static store.util.constant.Delimiter.COMMA;

import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

public class Stock {
    private final ProductName productName;
    private final Price price;
    private Quantity quantity;
    private final PromotionName promotionName;

    private Stock(ProductName productName, Price price, Quantity quantity, PromotionName promotionName) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public static Stock from(String inputStock) {
        String[] split = inputStock.split(COMMA.getDelimiter());
        String name = split[NAME_INDEX.getIndex()];
        Long price = Long.parseLong(split[PRICE_INDEX.getIndex()]);
        String quantity = split[QUANTITY_INDEX.getIndex()];
        String promotionName = split[PROMOTION_INDEX.getIndex()];
        return createStock(name, price, quantity, promotionName);
    }

    private static Stock createStock(String name, Long price, String quantity, String promotionName) {
        return new Stock(
                ProductName.valueOf(name),
                Price.valueOf(price),
                Quantity.valueOf(quantity),
                PromotionName.valueOf(promotionName)
        );
    }

    public String format() {
        return STOCK_FORMAT.formatStock(productName, price, quantity.getQuantity(), promotionName);
    }

    public boolean isApplyPromotion() {
        return promotionName != null;
    }

    public boolean matchesProductName(String name) {
        return productName.matchesName(name);
    }

    public void decreaseQuantity(Long quantity) {
        this.quantity = this.quantity.decrease(quantity);
    }

    public Long calculateAmount(Long quantity) {
        return quantity * price.getPrice();
    }

    public ProductName getProductName() {
        return productName;
    }

    public Price getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity.getQuantity();
    }

    public String getPromotionName() {
        return promotionName.getPromotionName();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "productName=" + productName +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotion=" + promotionName +
                '}';
    }
}
