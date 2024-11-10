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
        StockRepository stockRepository = createStockRepository();
        PromotionRepository promotionRepository = createPromotionRepository();
        return new StoreController(
                createOrderService(stockRepository, promotionRepository),
                createBillingService(stockRepository, promotionRepository),
                stockRepository,
                createInputView(),
                createOutputView()
        );
    }

    public StockRepository createStockRepository() {
        return new StockRepository();
    }

    public PromotionRepository createPromotionRepository() {
        return new PromotionRepository();
    }

    public OrderService createOrderService(StockRepository stockRepository, PromotionRepository promotionRepository) {
        return new OrderService(stockRepository, promotionRepository);
    }

    public BillingService createBillingService(StockRepository stockRepository, PromotionRepository promotionRepository) {
        return new BillingService(stockRepository, promotionRepository);
    }

    public InputView createInputView() {
        return new InputView();
    }

    public OutputView createOutputView() {
        return new OutputView();
    }

}
