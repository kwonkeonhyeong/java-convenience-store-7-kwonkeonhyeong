package store.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.domain.order.Order;
import store.domain.order.Orders;
import store.repository.PromotionRepository;
import store.repository.StockRepository;

class OrderServiceTest {

    private static OrderService orderService;

    @BeforeAll
    static void BeforeAll() {
        StockRepository stockRepository = new StockRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        orderService = new OrderService(stockRepository, promotionRepository);
    }

    @Test
    void 입력_받은_주문_내용으로_주문_목록_생성() {
        Orders orders = orderService.createOrders("[콜라-3],[에너지바-5]");
        List<String> expectedNames = List.of("콜라", "에너지바");
        List<String> orderNames = orders.getOrders()
                .stream()
                .map(Order::getName)
                .toList();
        assertThat(orderNames).containsAll(expectedNames);
    }

    @Test
    void 주문이_프로모션_적용_상품_수량을_적게_가져온_경우_무료_하나_추가_TRUE() {
        Order order = Order.from("[콜라-2]");
        assertThat(orderService.needsAdditionalOrder(order)).isTrue();
    }

    @Test
    void 주문이_프로모션_적용_상품_수량을_알맞게_가져온_경우_무료_하나_추가_False() {
        Order order = Order.from("[콜라-3]");
        assertThat(orderService.needsAdditionalOrder(order)).isFalse();
    }

    @Test
    void 주문에서_프로모션_재고_초과시_프로모션_적용하지_않는_일반수량_확인() {
        Order order = Order.from("[콜라-12]");
        assertThat(orderService.calculatePurchaseQuantityWithoutPromotion(order)).isEqualTo(3L);
    }

}