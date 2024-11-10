package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import store.domain.billing.Bill;
import store.domain.billing.BillingItem;
import store.domain.discount.DiscountPolicy;
import store.domain.discount.MembershipDiscountPolicy;
import store.domain.discount.NonDiscountPolicy;
import store.domain.vo.Answer;
import store.domain.order.Order;
import store.service.BillingService;
import store.service.OrderService;
import store.domain.order.Orders;
import store.service.StockService;
import store.view.InputView;
import store.view.OutputView;

/*
* StoreController에서 편의점 관련 로직 흐름을 관리
* */
public class StoreController {

    private final OrderService orderService;
    private final BillingService billingService;
    private final StockService stockService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(OrderService orderService, BillingService billingService, StockService stockService,
                           InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.billingService = billingService;
        this.stockService = stockService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        do {
            displayCurrentStockState();
            Bill bill = completeOrder();
            displayBill(bill);
            updateStock(bill);
        } while (doLoop(this::determineAnotherOrder));
    }

    public Orders receiveOrder() {
        String input = inputView.enterOrder();
        return orderService.createOrders(input);
    }

    private Bill completeOrder() {
        Orders orders = doLoop(this::receiveOrder);
        checkPromotion(orders);
        DiscountPolicy discountPolicy = doLoop(this::determineMembershipDiscount);
        return generateBill(orders, discountPolicy);
    }

    private void displayBill(Bill bill) {
        outputView.printBill(bill.formatBill());
    }

    private void updateStock(Bill bill) {
        orderService.updateStock(bill.getBillingItems());
    }

    private void checkPromotion(Orders orders) {
        for (Order order : orders.getOrders()) {
            if (orderService.needsAdditionalOrder(order)) {
                checkAddOrder(order);
            }
            Long quantity = orderService.confirmPurchaseWithoutPromotion(order);
            if (quantity != 0L) {
                checkNonPromotionalQuantity(order, quantity);
            }
        }
    }

    private void checkAddOrder(Order order) {
        Answer answer = doLoop(() -> receiveAdditionalOrder(order));
        if (answer.isYes()) {
            order.addQuantity();
        }
    }

    private Answer receiveAdditionalOrder(Order order) {
        String input = inputView.enterAdditionalOrder(order.getName());
        return Answer.from(input);
    }

    private void checkNonPromotionalQuantity(Order order, Long quantity) {
        Answer answer = doLoop(() -> determineNonPromotionPurchase(order, quantity));
        if (!answer.isYes()) {
            order.removeQuantity(quantity);
        }
    }

    private Answer determineNonPromotionPurchase(Order order, Long quantity) {
        String input = inputView.enterNonPromotionPurchase(order.getName(), quantity);
        return Answer.from(input);
    }

    private Bill generateBill(Orders orders, DiscountPolicy discountPolicy) {
        List<BillingItem> billingItems = new ArrayList<>();
        for (Order order : orders.getOrders()) {
            billingItems.add(billingService.generateBillingItem(order));
        }
        return Bill.of(billingItems, discountPolicy);
    }

    private DiscountPolicy determineMembershipDiscount() {
        String input = inputView.enterApplicableMembershipDiscount();
        Answer answer = Answer.from(input);
        if(answer.isYes()) {
            return MembershipDiscountPolicy.newInstance();
        }
        return NonDiscountPolicy.newInstance();
    }

    private boolean determineAnotherOrder() {
        String input = inputView.enterAnotherOrder();
        Answer answer = Answer.from(input);
        return answer.isYes();
    }

    private void displayCurrentStockState() {
        outputView.printStocksStateHeader();
        outputView.printCurrentStockState(stockService.formatCurrentStockState());
    }

    private <T> T doLoop(Supplier<T> function) {
        while (true) {
            try {
                return function.get();
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }

}
