package store.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import store.FileHandler;
import store.domain.Stock;
import store.domain.product.ProductName;

public class StockRepository {

    public static final List<Stock> store = new ArrayList<>();

    static {
        try {
            List<String> strings = FileHandler.readFromFile("src/main/resources/products.md");
            strings.removeFirst();
            for (String string : strings) {
                Stock stock = Stock.from(string);
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
