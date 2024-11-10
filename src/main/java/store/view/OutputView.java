package store.view;

public class OutputView {

    private static final String CURRENT_STOCK_STATE_HEADER = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";

    public void printCurrentStockState(String currentState) {
        System.out.println(currentState);
    }

    public void printStocksStateHeader() {
        System.out.println(CURRENT_STOCK_STATE_HEADER);
        System.out.println();
    }

    public void printBill(String bill) {
        System.out.println();
        System.out.println(bill);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
