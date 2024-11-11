package store.service;

import static store.domain.constant.StockPattern.STOCK_FORMAT;

import java.util.List;
import store.domain.billing.BillingItem;
import store.domain.stock.Stock;
import store.domain.vo.ProductName;
import store.repository.StockRepository;

public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public String formatCurrentStockState() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ProductName> uniqueProductNames = stockRepository.findUniqueProductNames();
        for (ProductName name : uniqueProductNames) {
            Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(name.getName());
            Stock normalStock = stockRepository.findByProductNameAndPromotionIsNull(name.getName());
            stringBuilder.append(formatStockInfo(promotionStock, normalStock));
        }
        return stringBuilder.toString();
    }

    public void updateStock(List<BillingItem> billingItems) {
        for (BillingItem billingItem : billingItems) {
            if (billingItem.hasPromotion()) {
                updatePromotionStock(billingItem);
                continue;
            }
            updateNormalStock(billingItem);
        }
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

    private void updatePromotionStock(BillingItem billingItem) {
        if (billingItem.hasNoStock()) {
            Long quantity = billingItem.calculateDeficitQuantity();
            decreasePromotionStockQuantity(billingItem.getOrderProductName(), billingItem.getStockQuantity());
            decreaseNormalStockQuantity(billingItem.getOrderProductName(), quantity);
            return;
        }
        decreasePromotionStockQuantity(billingItem.getOrderProductName(), billingItem.getOrderQuantity());
    }

    private void updateNormalStock(BillingItem billingItem) {
        if (billingItem.hasNoStock()) {
            decreaseNormalStockQuantity(billingItem.getOrderProductName(), billingItem.getStockQuantity());
            return;
        }
        decreaseNormalStockQuantity(billingItem.getOrderProductName(), billingItem.getOrderQuantity());
    }

    private void decreasePromotionStockQuantity(String name, Long quantity) {
        stockRepository.decreaseQuantityWhereNameAndPromotionIsNotNull(name, quantity);
    }

    private void decreaseNormalStockQuantity(String name, Long quantity) {
        stockRepository.decreaseQuantityWhereNameAndPromotionIsNull(name, quantity);
    }

}
