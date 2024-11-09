package store.service;

import store.domain.BillingItem;
import store.domain.Order;
import store.domain.Promotion;
import store.domain.Stock;
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
        if(stock != null) {
            Promotion promotion = promotionRepository.findByPromotionName(stock.getPromotionName());
            return BillingItem.of(order,stock,promotion);
        }
        stock = stockRepository.findByProductNameAndPromotionIsNull(name);
        return BillingItem.of(order,stock,null);
    }
}
