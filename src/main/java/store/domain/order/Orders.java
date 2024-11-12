package store.domain.order;

import static store.util.constant.Delimiter.COMMA;
import static store.util.constant.ErrorMessage.INVALID_FORMAT;
import static store.util.constant.ErrorMessage.INVALID_INPUT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders {

    private final List<Order> orders;

    private Orders(List<Order> orders) {
        this.orders = orders;
    }

    public static Orders from(String input) {
        validateEmpty(input);
        List<Order> orders = Arrays.stream(input.split(COMMA.getDelimiter(), -1))
                .map(Order::from).toList();
        validateDuplication(orders);
        return new Orders(orders);
    }

    private static void validateEmpty(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

    private static void validateDuplication(List<Order> orders) {
        Set<Order> uniqueOrder = new HashSet<>(orders);
        if (uniqueOrder.size() != orders.size()) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orders=" + orders +
                '}';
    }
}
