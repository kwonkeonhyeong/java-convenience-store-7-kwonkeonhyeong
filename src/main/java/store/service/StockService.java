package store.service;

import static store.domain.constant.StockPattern.STOCK_FORMAT;

import java.util.List;
import store.domain.Stock;
import store.domain.vo.ProductName;
import store.repository.StockRepository;

public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public String formatCurrentStockState() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ProductName> uniqueProductNames = stockRepository.findDistinctProductNames();
        for (ProductName name : uniqueProductNames) {
            Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(name.getName());
            Stock normalStock = stockRepository.findByProductNameAndPromotionIsNull(name.getName());
            stringBuilder.append(formatStockInfo(promotionStock, normalStock));
        }
        return stringBuilder.toString();
    }

    private String formatStockInfo(Stock promotionStock, Stock normalStock) {
        StringBuilder stringBuilder = new StringBuilder();
        formatStock(stringBuilder,promotionStock);
        formatStock(stringBuilder,normalStock);
        formatOutOfStock(stringBuilder,normalStock, promotionStock);
        return stringBuilder.toString();
    }

    private void formatStock(StringBuilder stringBuilder, Stock stock) {
        if (stock != null) {
            stringBuilder.append(stock.format());
        }
    }

    private void formatOutOfStock(StringBuilder stringBuilder, Stock normalStock, Stock promotionStock) {
        if (normalStock == null && promotionStock != null) {
            String formatted = STOCK_FORMAT.formatStock(promotionStock.getProductName(), promotionStock.getPrice(), 0L,
                    null);
            stringBuilder.append(formatted);
        }
    }

}