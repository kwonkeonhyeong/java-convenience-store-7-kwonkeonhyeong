package store.domain;

import java.util.Objects;
import store.domain.product.Price;
import store.domain.product.ProductName;
import store.domain.product.Promotion;
import store.domain.product.Quantity;

public class Stock {
    private final ProductName productName;
    private final Price price;
    private final Quantity quantity;
    private final Promotion promotion;

    private Stock(ProductName productName, Price price, Quantity quantity, Promotion promotion) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Stock from(String inputStock) {
        String[] split = inputStock.split(",");
        String name = split[0];
        Long price = Long.parseLong(split[1]);
        Long quantity = Long.parseLong(split[2]);
        String promotion = split[3];
        return createStock(name, price, quantity, promotion);
    }

    private static Stock createStock(String name, Long price, Long quantity, String promotion) {

        return new Stock(
                ProductName.valueOf(name),
                Price.valueOf(price),
                Quantity.valueOf(quantity),
                Promotion.valueOf(promotion)
        );
    }

    public String format() {
        String formattedPrice = String.format("%,d", price.getPrice());
        if(promotion == null) {
            return String.format("- %s %s원 %s개",productName,formattedPrice,quantity);
        }
        return String.format("- %s %s원 %s개 %s",productName,formattedPrice,quantity,promotion);
    }

    public boolean isApplyPromotion() {
        return promotion != null;
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
                ", promotion=" + promotion +
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
