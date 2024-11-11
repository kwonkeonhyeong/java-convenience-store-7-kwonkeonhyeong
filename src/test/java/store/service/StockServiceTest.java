package store.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.billing.Bill;
import store.domain.discount.MembershipDiscountPolicy;
import store.domain.order.Orders;
import store.domain.stock.Stock;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

class StockServiceTest {

    private static BillingService billingService;
    private static OrderService orderService;
    private static StockService stockService;
    private static StockRepository stockRepository;

    private Bill bill;

    @BeforeAll
    static void BeforeAll() {
        stockRepository = new StockRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        billingService = new BillingService(stockRepository, promotionRepository);
        orderService = new OrderService(stockRepository, promotionRepository);
        stockService = new StockService(stockRepository);
    }

    @BeforeEach
    void beforeEach() {
        Orders orders = orderService.createOrders("[콜라-15]");
        bill = billingService.generateBill(orders, MembershipDiscountPolicy.newInstance());
    }

    @Test
    void 구매_수량만큼_재고_업데이트_확인() {
        stockService.updateStock(bill.getBillingItems());
        Stock stock = stockRepository.findByProductNameAndPromotionIsNull("콜라");
        Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull("콜라");

        assertThat(stock.getQuantity()).isEqualTo(5);
        assertThat(promotionStock.getQuantity()).isEqualTo(0);
    }

}