package store.config;

import store.controller.StoreController;
import store.repository.PromotionRepository;
import store.repository.StockRepository;
import store.service.BillingService;
import store.service.OrderService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    public StoreController createStoreController() {
        return new StoreController(
                createOrderService(),
                createBillingService(),
                new StockRepository(),
                createInputView(),
                createOutputView()
        );
    }

    public OrderService createOrderService() {
        return new OrderService(new StockRepository(), new PromotionRepository());
    }

    public BillingService createBillingService() {
        return new BillingService(new StockRepository(), new PromotionRepository());
    }

    public InputView createInputView() {
        return new InputView();
    }

    public OutputView createOutputView() {
        return new OutputView();
    }

}
