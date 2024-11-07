package store.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.FileHandler;
import store.Stock;

public class StorkRepository {

    public static final Map<Long, Stock> store = new HashMap<>();
    public static Long sequence = 0L;

    static {
        try {
            List<String> strings = FileHandler.readFromFile("src/main/resources/products.md");
            strings.removeFirst();
            for (String string : strings) {
                Stock stock = Stock.from(string);
                store.put(sequence++,stock);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Stock> findAll() {
        return store.values().stream().toList();
    }

    public List<Stock> findByPromotionIsNotNull() {
        return store.values()
                .stream()
                .filter(Stock::isApplyPromotion)
                .toList();
    }

    public List<Stock> findByPromotionIsNull() {
        return store.values()
                .stream()
                .filter(stock -> !stock.isApplyPromotion())
                .toList();
    }

    public List<Stock> findByName(String name) {
        return store.values().stream().filter(stock -> stock.matchesName(name)).toList();
    }

}
