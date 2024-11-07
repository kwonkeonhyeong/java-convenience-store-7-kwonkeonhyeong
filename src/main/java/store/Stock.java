package store;

import java.util.Objects;

public class Stock {
    private final String name;
    private final long price;
    private final long quantity;
    private final String promotion;

    private Stock(String name, long price, long quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Stock from(String inputStock) {
        String[] split = inputStock.split(",");
        String name = split[0];
        long price = Long.parseLong(split[1]);
        long quantity = Long.parseLong(split[2]);
        String promotion = split[3];

        return new Stock(name, price, quantity, promotion);
    }

    public boolean isApplyPromotion() {
        return !promotion.equals("null");
    }

    public boolean matchesName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock stock)) {
            return false;
        }
        return Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotion='" + promotion + '\'' +
                '}';
    }
}
