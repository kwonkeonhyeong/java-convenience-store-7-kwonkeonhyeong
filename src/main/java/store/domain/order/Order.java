package store.domain.order;

import static store.util.constant.Delimiter.DASH;
import static store.util.constant.ErrorMessage.INVALID_FORMAT;

import java.util.Objects;
import store.domain.vo.ProductName;
import store.domain.vo.Quantity;

public class Order {

    private static final String OPEN_BRACKET = "[";
    private static final String CLOSE_BRACKET = "]";
    private static final int PRODUCT_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;

    private final ProductName name;
    private Quantity quantity;

    private Order(ProductName name, Quantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static Order from(String input) {
        validateOrderFormat(input);
        String substring = input.substring(1, input.length() - 1);
        String[] split = substring.split(DASH.getDelimiter(), -1);
        ProductName productName = ProductName.valueOf(split[PRODUCT_INDEX]);
        Quantity quantity = Quantity.valueOf(split[QUANTITY_INDEX]);
        return new Order(productName, quantity);
    }

    private static void validateOrderFormat(String input) {
        if (!(input.startsWith(OPEN_BRACKET) && input.endsWith(CLOSE_BRACKET))) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }

        if (!input.contains(DASH.getDelimiter())) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    public void addQuantity() {
        quantity = quantity.add();
    }

    public void removeQuantity(Long quantity) {
        this.quantity = this.quantity.decrease(quantity);
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
