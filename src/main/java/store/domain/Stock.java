package store.domain;

import java.util.ArrayList;
import java.util.List;
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
        String[] split = inputStock.split(",");
        String name = split[0];
        Long price = Long.parseLong(split[1]);
        String quantity = split[2];
        String promotionName = split[3];
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
        String formattedPrice = String.format("%,d", price.getPrice());
        if (promotionName == null) {
            return formatNormalStock(formattedPrice);
        }
        return formatPromotionStock(formattedPrice);
    }

    private String formatPromotionStock(String formattedPrice) {
        if (quantity.getQuantity() == 0L) {
            return String.format("- %s %s원 재고 없음 %s", productName, formattedPrice, promotionName);
        }
        return String.format("- %s %s원 %s개 %s", productName, formattedPrice, quantity, promotionName);
    }

    private String formatNormalStock(String formattedPrice) {
        if (quantity.getQuantity() == 0L) {
            return String.format("- %s %s원 재고 없음", productName, formattedPrice);
        }
        return String.format("- %s %s원 %s개", productName, formattedPrice, quantity);
    }

    public String formatStockData() {
        List<String> format = new ArrayList<>();
        format.add(productName.getName());
        format.add(String.valueOf(price.getPrice()));
        format.add(String.valueOf(quantity.getQuantity()));
        if(promotionName != null ) {
            format.add(promotionName.getPromotionName());
        }
        format.add("null");
        return String.join(",",format);
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
        return quantity* price.getPrice();
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
