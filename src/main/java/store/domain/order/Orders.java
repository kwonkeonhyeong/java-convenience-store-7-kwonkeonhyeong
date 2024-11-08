package store.domain.order;

import java.util.Arrays;
import java.util.List;

public class Orders {

    private final List<Order> orders;

    private Orders(List<Order> orders) {
        this.orders = orders;
    }

    public static Orders from(String input) {
        validate(input);
        List<Order> orders = Arrays.stream(input.split(",", -1))
                .map(Order::from).toList();
        return new Orders(orders);
    }

    private static void validate(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
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
