package store.repository;

import static store.util.constant.FilePath.PRODUCTS_FILE_PATH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import store.util.FileHandler;
import store.domain.stock.Stock;
import store.domain.vo.ProductName;
/*
* 재고와 관련된 데이터를 로드하고 이를 관리하는 책임 부여
* 재고 데이터가 필요한 다른 객체에게 데이터를 제공하는 책임 부여
* */
public class StockRepository {

    private final List<Stock> store = new ArrayList<>();

    public StockRepository() {
        loadData();
    }

    private void loadData() {
        try {
            List<String> inputs = FileHandler.readFromFile(PRODUCTS_FILE_PATH.getPath());
            registerData(inputs);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerData(List<String> inputs) {
        inputs.removeFirst();
        for (String input : inputs) {
            Stock stock = Stock.from(input);
            store.add(stock);
        }
    }

    public List<ProductName> findDistinctProductNames() {
        return store.stream()
                .map(Stock::getProductName)
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
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

    public void decreaseQuantityWhereNameAndPromotionIsNull(String name, Long quantity) {
        Stock target = findByProductNameAndPromotionIsNull(name);
        target.decreaseQuantity(quantity);
    }

    public void decreaseQuantityWhereNameAndPromotionIsNotNull(String name, Long quantity) {
        Stock target = findByProductNameAndPromotionIsNotNull(name);
        target.decreaseQuantity(quantity);
    }

}
