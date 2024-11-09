package store.service;

import java.util.List;
import store.domain.BillingItem;
import store.domain.Order;
import store.domain.Orders;
import store.domain.Promotion;
import store.domain.Stock;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

public class OrderService {

    private final StockRepository stockRepository;
    private final PromotionRepository promotionRepository;

    public OrderService(StockRepository stockRepository, PromotionRepository promotionRepository) {
        this.stockRepository = stockRepository;
        this.promotionRepository = promotionRepository;
    }

    public Orders createOrders(String input) {
        Orders orders = Orders.from(input);
        for (Order order : orders.getOrders()) {
            checkOrderProductExists(order);
            hasStockQuantityAvailable(order);
        }
        return orders;
    }

    private void checkOrderProductExists(Order order) {
        if (!stockRepository.hasStockWith(order.getName())) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void hasStockQuantityAvailable(Order order) {
        List<Stock> productNames = stockRepository.findByProductName(order.getName());
        long availableStockQuantitySum = productNames.stream()
                .map(Stock::getQuantity)
                .mapToLong(Long::longValue)
                .sum();
        if (availableStockQuantitySum < order.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public boolean needsAdditionalOrder(Order order) {
        Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(order.getName());
        if (promotionStock == null) {
            return false;
        }
        Promotion promotion = promotionRepository.findByPromotionName(promotionStock.getPromotionName());
        if (promotion.isNonPromotionDate()) {
            return false;
        }
        return promotion.isAdditionalOrder(order, promotionStock);
    }

    public Long confirmPurchaseWithoutPromotion(Order order) {
        Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(order.getName());
        if (promotionStock == null) {
            return 0L;
        }
        Promotion promotion = promotionRepository.findByPromotionName(promotionStock.getPromotionName());
        if (promotion.isNonPromotionDate()) {
            return 0L;
        }
        return promotion.calculateNonPromotionalQuantity(order, promotionStock);
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
