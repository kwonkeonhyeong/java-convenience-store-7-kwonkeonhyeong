package store.service;

import java.util.List;
import store.domain.order.Order;
import store.domain.order.Orders;
import store.domain.stock.Promotion;
import store.domain.stock.Stock;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

public class OrderService {

    private static final String PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private static final String OUT_OF_STOCK = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    private static final Long NON_PROMOTION_QUANTITY = 0L;

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
            return NON_PROMOTION_QUANTITY;
        }
        Promotion promotion = promotionRepository.findByPromotionName(promotionStock.getPromotionName());
        if (promotion.isNonPromotionDate()) {
            return NON_PROMOTION_QUANTITY;
        }
        return promotion.calculateNonPromotionalQuantity(order, promotionStock);
    }

    private void hasStockQuantityAvailable(Order order) {
        List<Stock> productNames = stockRepository.findByProductName(order.getName());
        long availableStockQuantitySum = productNames.stream()
                .map(Stock::getQuantity)
                .mapToLong(Long::longValue)
                .sum();
        if (availableStockQuantitySum < order.getQuantity()) {
            throw new IllegalArgumentException(OUT_OF_STOCK);
        }
    }

    private void checkOrderProductExists(Order order) {
        if (!stockRepository.hasStockWith(order.getName())) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
        }
    }

}
