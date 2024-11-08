package store.service;

import java.util.List;
import store.domain.Order;
import store.domain.Orders;
import store.domain.Promotion;
import store.domain.Stock;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

public class OrderService {

    private final StockRepository stockRepository;
    private final PromotionRepository promotionRepository;

    public OrderService() {
        this.stockRepository = new StockRepository();
        this.promotionRepository = new PromotionRepository();
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
        if (!promotion.checkDate()) {
            return false;
        }
        return promotion.isAdditionalOrder(order,promotionStock);
    }

    public Long confirmPurchaseWithoutPromotion(Order order) {
        Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(order.getName());
        if (promotionStock == null) {
            return 0L;
        }
        Promotion promotion = promotionRepository.findByPromotionName(promotionStock.getPromotionName());
        if (!promotion.checkDate()) {
            return 0L;
        }
        return promotion.calculateNonPromotionalQuantity(order, promotionStock);
    }
}
