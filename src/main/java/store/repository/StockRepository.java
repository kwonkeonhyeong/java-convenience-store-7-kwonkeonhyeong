package store.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import store.FileHandler;
import store.domain.stock.Stock;
import store.domain.stock.vo.ProductName;

public class StockRepository {

    public static final List<Stock> store = new ArrayList<>();

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

    public Stock findByProductNameAndPromotionIsNotNull(ProductName productName) {
        return store.stream()
                .filter(stock -> stock.matchesProductName(productName) && stock.isApplyPromotion())
                .findFirst()
                .orElse(null);
    }

    public Stock findByProductNameAndPromotionIsNull(ProductName productName) {
        return store.stream()
                .filter(stock -> {
                        if (!stock.matchesProductName(productName)) return false;
                        return !stock.isApplyPromotion();
                        })
                .findFirst()
                .orElse(null);
    }
}
