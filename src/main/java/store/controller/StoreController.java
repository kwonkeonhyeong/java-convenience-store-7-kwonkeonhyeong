package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import store.domain.Bill;
import store.domain.BillingItem;
import store.domain.vo.Answer;
import store.domain.Order;
import store.service.BillingService;
import store.service.OrderService;
import store.domain.Orders;
import store.domain.Stock;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.repository.StockRepository;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StockRepository stockRepository = new StockRepository();
    private final OrderService orderService = new OrderService();
    private final BillingService billingService = new BillingService();

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        displayCurrentStockState();
        Orders orders = doLoop(this::receiveOrder);
        checkPromotion(orders);
        boolean hasMembership = doLoop(this::determineMembershipDiscount);
        Bill bill = generateBill(orders, hasMembership);
    }

    public Orders receiveOrder() {
        String input = inputView.enterOrder();
        return orderService.createOrders(input);
    }

    private void checkPromotion(Orders orders) {
        for (Order order : orders.getOrders()) {
            if (orderService.needsAdditionalOrder(order)) {
                checkAddOrder(order);
            }
            Long quantity = orderService.confirmPurchaseWithoutPromotion(order);
            if ( quantity != 0L) {
                checkNonPromotionalQuantity(order,quantity);
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

    private void checkNonPromotionalQuantity(Order order,Long quantity) {
        Answer answer = doLoop(() -> determineNonPromotionPurchase(order,quantity));
        if (!answer.isYes()) {
            order.removeQuantity(quantity);
        }
    }

    private Answer determineNonPromotionPurchase(Order order, Long quantity) {
        String input = inputView.enterNonPromotionPurchase(order.getName(),quantity);
        return Answer.from(input);
    }

    private Bill generateBill(Orders orders, boolean hasMembership) {
        List<BillingItem> billingItems = new ArrayList<>();
        for (Order order : orders.getOrders()) {
            billingItems.add(billingService.generateBillingItem(order));
        }
        orderService.updateStock(billingItems);
        return Bill.of(billingItems,hasMembership);
    }

    private boolean determineMembershipDiscount() {
        String input = inputView.enterApplicableMembershipDiscount();
        Answer answer = Answer.from(input);
        return answer.isYes();
    }

    private void displayCurrentStockState() {
        outputView.printStocksStateHeader();
        List<ProductName> uniqueProductNames = stockRepository.findDistinctProductNames();
        for (ProductName name : uniqueProductNames) {
            Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(name.getName());
            Stock normalStock = stockRepository.findByProductNameAndPromotionIsNull(name.getName());
            displayStockInfo(promotionStock, normalStock);
        }
    }

    private void displayStockInfo(Stock promotionStock, Stock normalStock) {
        displayStock(promotionStock);
        displayStock(normalStock);
        displayOutOfStock(normalStock, promotionStock);
    }

    private void displayStock(Stock stock) {
        if (stock != null) {
            outputView.printCurrentStockState(stock.format());
        }
    }

    private void displayOutOfStock(Stock normalStock, Stock promotionStock) {
        if (normalStock == null && promotionStock != null) {
            Price price = promotionStock.getPrice();
            String formattedPrice = String.format("%,d", price.getPrice());
            System.out.printf("- %s %s원 재고 없음%n", promotionStock.getProductName(), formattedPrice);
        }
    }

    private <T> T doLoop(Supplier<T> function) {
        while (true) {
            try {
                return function.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
