package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.billing.Bill;
import store.domain.billing.BillingItem;
import store.domain.discount.DiscountPolicy;
import store.domain.order.Order;
import store.domain.order.Orders;
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

    public Bill generateBill(Orders orders, DiscountPolicy discountPolicy) {
        List<BillingItem> billingItems = new ArrayList<>();
        for (Order order : orders.getOrders()) {
            billingItems.add(generateBillingItem(order));
        }
        return Bill.of(billingItems, discountPolicy);
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
