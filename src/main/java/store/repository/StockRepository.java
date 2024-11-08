package store.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import store.FileHandler;
import store.domain.Stock;
import store.domain.ProductName;

public class StockRepository {

    private static final List<Stock> store = new ArrayList<>();

    static {
        try {
            List<String> inputs = FileHandler.readFromFile("src/main/resources/products.md");
            inputs.removeFirst();
            for (String input : inputs) {
                Stock stock = Stock.from(input);
                store.add(stock);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ProductName> findDistinctProductNames() {
        return store.stream()
                .map(Stock::getProductName)
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
                .toList();
    }

    public List<Stock> findByPromotionIsNotNull() {
        return store.stream()
                .filter(Stock::isApplyPromotion)
                .toList();
    }

    public List<Stock> findByPromotionIsNull() {
        return store.stream()
                .filter(stock -> !stock.isApplyPromotion())
                .toList();
    }

    public Stock findByProductNameAndPromotionIsNotNull(String name) {
        return store.stream()
                .filter(stock -> stock.matchesProductName(name) && stock.isApplyPromotion())
                .findFirst()
                .orElse(null);
    }

    public Stock findByProductNameAndPromotionIsNull(String name) {
        return store.stream()
                .filter(stock -> {
                    if (!stock.matchesProductName(name)) {
                        return false;
                    }
                    return !stock.isApplyPromotion();
                })
                .findFirst()
                .orElse(null);
    }

    public boolean hasStockWith(String name) {
        return store.stream()
                .anyMatch(stock -> stock.matchesProductName(name));
    }

    public List<Stock> findByProductName(String name) {
        return store.stream()
                .filter(stock -> stock.matchesProductName(name))
                .toList();
    }
}