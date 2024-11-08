package store.controller;

import java.util.List;
import java.util.function.Supplier;
import store.domain.order.Order;
import store.domain.order.Orders;
import store.domain.stock.Stock;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.repository.StockRepository;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StockRepository stockRepository = new StockRepository();

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        displayCurrentStockState();
        Orders orders = doLoop(this::getOrder);
    }

    public Orders getOrder() {
        String input = inputView.enterOrder();
        Orders orders = Orders.from(input);
        checkOrderProductExists(orders);
        hasStockQuantityAvailable(orders);
        return orders;
    }

    private void checkOrderProductExists(Orders orders) {
        for (Order order : orders.getOrders()) {
            if (!stockRepository.hasStockWith(order.getName())) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
            ;
        }
    }

    private void hasStockQuantityAvailable(Orders orders) {
        for (Order order : orders.getOrders()) {
            List<Stock> productNames = stockRepository.findByProductName(order.getName());
            long availableStockQuantitySum = productNames.stream()
                    .map(Stock::getQuantity)
                    .mapToLong(Long::longValue)
                    .sum();
            if (availableStockQuantitySum < order.getQuantity()) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    private void displayCurrentStockState() {
        outputView.printStocksStateHeader();
        List<ProductName> uniqueProductNames = stockRepository.findDistinctProductNames();
        for (ProductName name : uniqueProductNames) {
            Stock promotionStock = stockRepository.findByProductNameAndPromotionIsNotNull(name);
            Stock normalStock = stockRepository.findByProductNameAndPromotionIsNull(name);
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
