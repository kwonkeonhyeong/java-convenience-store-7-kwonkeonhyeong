package store.config;

import store.controller.StoreController;
import store.repository.PromotionRepository;
import store.repository.StockRepository;
import store.service.BillingService;
import store.service.OrderService;
import store.service.StockService;
import store.view.InputView;
import store.view.OutputView;

/*
AppConfig 에서 객체 생성 기능 담당
사용하는 Service, Repository, Controller, View 가 늘어나니 Controller에게 생성 책임을 몰아주는 것에는 한계가 있음
이를 해결하기 위해서 AppConfig를 구현해 보았습니다.
* */
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