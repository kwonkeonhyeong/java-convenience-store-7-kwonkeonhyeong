package store.service;

import store.domain.billing.BillingItem;
import store.domain.order.Order;
import store.domain.stock.Promotion;
import store.domain.stock.Stock;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

public class BillingService {

    private final StockRepository stockRepository;
    private final PromotionRepository promotionRepository;

    public BillingService(StockRepository stockRepository, PromotionRepository promotionRepository) {
        this.stockRepository = stockRepository;
        this.promotionRepository = promotionRepository;
    }

    public BillingItem generateBillingItem(Order order) {
        String name = order.getName();
        Stock stock = stockRepository.findByProductNameAndPromotionIsNotNull(name);
        if (stock != null) {
            Promotion promotion = promotionRepository.findByPromotionName(stock.getPromotionName());
            return BillingItem.of(order, stock, promotion);
        }
        stock = stockRepository.findByProductNameAndPromotionIsNull(name);
        return BillingItem.of(order, stock, null);
    }

}
