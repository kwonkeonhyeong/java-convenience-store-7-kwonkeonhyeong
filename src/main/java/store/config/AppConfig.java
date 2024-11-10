package store.config;

import store.controller.StoreController;
import store.repository.PromotionRepository;
import store.repository.StockRepository;
import store.service.BillingService;
import store.service.OrderService;
import store.service.StockService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    public StoreController createStoreController() {
        StockRepository stockRepository = createStockRepository();
        PromotionRepository promotionRepository = createPromotionRepository();
        return new StoreController(
                createOrderService(stockRepository, promotionRepository),
                createBillingService(stockRepository, promotionRepository),
                createStockService(stockRepository),
                createInputView(),
                createOutputView()
        );
    }

    private StockRepository createStockRepository() {
        return new StockRepository();
    }

    private PromotionRepository createPromotionRepository() {
        return new PromotionRepository();
    }

    private StockService createStockService(StockRepository stockRepository) {
        return new StockService(stockRepository);
    }

    private OrderService createOrderService(StockRepository stockRepository, PromotionRepository promotionRepository) {
        return new OrderService(stockRepository, promotionRepository);
    }

    private BillingService createBillingService(StockRepository stockRepository,
                                                PromotionRepository promotionRepository) {
        return new BillingService(stockRepository, promotionRepository);
    }

    private InputView createInputView() {
        return new InputView();
    }

    private OutputView createOutputView() {
        return new OutputView();
    }

}