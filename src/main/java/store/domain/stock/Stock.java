package store.domain.stock;

import java.util.Objects;
import store.domain.promotion.PromotionName;
import store.domain.stock.vo.Price;
import store.domain.stock.vo.ProductName;
import store.domain.stock.vo.Quantity;

public class Stock {
    private final ProductName productName;
    private final Price price;
    private final Quantity quantity;
    private final PromotionName promotionName;

    private Stock(ProductName productName, Price price, Quantity quantity, PromotionName promotionName) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public static Stock from(String inputStock) {
        String[] split = inputStock.split(",");
        String name = split[0];
        Long price = Long.parseLong(split[1]);
        Long quantity = Long.parseLong(split[2]);
        String promotionName = split[3];
        return createStock(name, price, quantity, promotionName);
    }

    private static Stock createStock(String name, Long price, Long quantity, String promotionName) {

        return new Stock(
                ProductName.valueOf(name),
                Price.valueOf(price),
                Quantity.valueOf(quantity),
                PromotionName.valueOf(promotionName)
        );
    }

    public String format() {
        String formattedPrice = String.format("%,d", price.getPrice());
        if(promotionName == null) {
            return String.format("- %s %s원 %s개",productName,formattedPrice,quantity);
        }
        return String.format("- %s %s원 %s개 %s",productName,formattedPrice,quantity,promotionName);
    }

    public boolean isApplyPromotion() {
        return promotionName != null;
    }

    public boolean matchesProductName(ProductName productName) {
        return this.productName.equals(productName);
    }

    public ProductName getProductName() {
        return productName;
    }

    public Price getPrice() {
        return price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock stock)) {
            return false;
        }
        return Objects.equals(productName, stock.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }
}
