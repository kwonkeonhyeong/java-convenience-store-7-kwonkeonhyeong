package store.domain;

import java.util.Objects;

public class Order {

    private final ProductName name;
    private Quantity quantity;

    private Order(ProductName name, Quantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Order from(String input) {
        validateOrderFormat(input);
        String substring = input.substring(1, input.length() - 1);
        String[] split = substring.split("-", -1);
        ProductName productName = ProductName.valueOf(split[0]);
        Quantity quantity = Quantity.valueOf(split[1]);
        return new Order(productName, quantity);
    }

    private static void validateOrderFormat(String input) {
        if (!(input.startsWith("[") && input.endsWith("]"))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        if (!input.contains("-")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public void addQuantity() {
        quantity = quantity.add();
    }

    public void removeQuantity(Long quantity) {
        this.quantity = this.quantity.remove(quantity);
    }

    public String getName() {
        return name.getName();
    }

    public Long getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order order)) {
            return false;
        }
        return Objects.equals(getName(), order.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Order{" +
                "name=" + name +
                ", quantity=" + quantity +
                '}';
    }
}
