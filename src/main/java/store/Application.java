package store;

import store.controller.StoreController;
import store.repository.PromotionRepository;
import store.repository.StockRepository;
import store.service.BillingService;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        StockRepository stockRepository = new StockRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        OrderService orderService = new OrderService(stockRepository,promotionRepository);
        BillingService billingService = new BillingService(stockRepository,promotionRepository);

        StoreController storeController = new StoreController(
                inputView,
                outputView,
                stockRepository,
                promotionRepository,
                orderService,
                billingService
        );

        storeController.run();
    }
}
