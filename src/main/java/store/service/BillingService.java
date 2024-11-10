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

/*
 * 주문 결과에 따라서 영수증을 만드는 로직을 제공
 * 영수증 내 아이템을 만들어서 영수증을 반환하는 로직이 Controller에 있을 필요가 있을까?
 * 이를 BillingService 에게 책임 전가 해보자~~ -> 바로 적용
 * */
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
