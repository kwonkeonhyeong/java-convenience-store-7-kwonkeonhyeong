package store.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.billing.Bill;
import store.domain.billing.BillingItem;
import store.domain.discount.MembershipDiscountPolicy;
import store.domain.order.Order;
import store.domain.order.Orders;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

class BillingServiceTest {
    private static BillingService billingService;
    private static OrderService orderService;

    private Bill bill;

    @BeforeAll
    static void BeforeAll() {
        StockRepository stockRepository = new StockRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        billingService = new BillingService(stockRepository, promotionRepository);
        orderService = new OrderService(stockRepository, promotionRepository);
    }

    @BeforeEach
    void beforeEach() {
        Orders orders = orderService.createOrders("[콜라-15],[에너지바-5]");
        bill = billingService.generateBill(orders, MembershipDiscountPolicy.newInstance());
    }

    @Test
    void 주문_목록을_기준으로_계산서_생성_확인() {
        assertThat(bill).isInstanceOf(Bill.class);
    }

    @Test
    void 주문에_해당하는_계산서_항목_생성() {
        assertThat(billingService.generateBillingItem(Order.from("[콜라-3]"))).isInstanceOf(BillingItem.class);
    }

    @Test
    void 주문에_해당하는_총_구매_금액_확인() {
        assertThat(bill.totalPurchaseAmount()).isEqualTo(25000);
    }

    @Test
    void 주문에_해당하는_총_주문_수량_확인() {
        assertThat(bill.totalOrderQuantity()).isEqualTo(20);
    }

    @Test
    void 주문에_해당하는_총_프로모션_적용_금액_확인() {
        assertThat(bill.totalPromotionQuantityAmount()).isEqualTo(9000);
    }

    @Test
    void 주문에_해당하는_총_프로모션_적용_할인_금액_확인() {
        assertThat(bill.totalPromotionDiscountAmount()).isEqualTo(3000);
    }

    @Test
    void 주문에_해당하는_총_멤버쉽_할인_금액_확인() {
        assertThat(bill.totalMembershipDiscountAmount()).isEqualTo(4800);
    }

    @Test
    void 주문에_해당하는_결제_금액_확인() {
        assertThat(bill.totalPaymentAmount()).isEqualTo(17200);
    }

}