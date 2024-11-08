package store.domain.order;

import store.domain.vo.ProductName;
import store.domain.vo.Quantity;

public class Order {

    private final ProductName name;
    private final Quantity quantity;

    private Order(ProductName name, Quantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Order from(String input) {
        validateOrderFormat(input);
        String substring = input.substring(1, input.length() - 1);
        String[] split = substring.split("-", -1);
        String name = split[0];
        String quantity = split[1];
        return new Order(ProductName.valueOf(name), Quantity.valueOf(quantity));
    }

    private static void validateOrderFormat(String input) {
        if (!(input.startsWith("[") && input.endsWith("]"))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        if (!input.contains("-")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public ProductName getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public String toString() {
        return "Order{" +
                "name=" + name +
                ", quantity=" + quantity +
                '}';
    }
}
