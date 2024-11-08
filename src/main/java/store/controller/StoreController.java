package store.controller;

import java.util.List;
import store.domain.Stock;
import store.domain.product.Price;
import store.domain.product.ProductName;
import store.repository.StockRepository;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private InputView inputView;
    private OutputView outputView;
    private StockRepository stockRepository = new StockRepository();

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        displayCurrentStockState();
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
}
