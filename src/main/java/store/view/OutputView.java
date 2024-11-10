package store.view;

/*
* 출력만 하는 형태로 구현해 보았음
* 출력 값에 대한 데이터 핸들링은 서비스 또는 도메인 쪽에 책임을 부여하였음.
* 다만 이렇게 구현하니 outputview가 너무 적은 책임을 가지고
* 도메인과 서비스 쪽에 과도하게 책임이 부여되지 않았나? 하는 생각이 들어서 리펙토링 고민해야함
* */
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
