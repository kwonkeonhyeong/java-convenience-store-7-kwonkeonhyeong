package store.service;

import static store.domain.constant.StockPattern.STOCK_FORMAT;

import java.util.List;
import store.domain.billing.BillingItem;
import store.domain.stock.Stock;
import store.domain.vo.ProductName;
import store.repository.StockRepository;
/*
* StockService에서 재고 관련 서비스를 모두 처리해주고 싶음
* 먼저 재고 불러오고 해당 재고들을 사용자에게 보여주기 위한 포맷을 만드는 기능을 할당
* 추가 적으로 재고를 업데이트 하는 기능도 이쪽으로 뺄 수 있을까?? -> 바로 적용 해봄 Nice
* */
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

    public void updateStock(List<BillingItem> billingItems) {
        for (BillingItem billingItem : billingItems) {
            if (billingItem.hasPromotion()) {
                updatePromotionStock(billingItem);
                continue;
            }
            updateNormalStock(billingItem);
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
