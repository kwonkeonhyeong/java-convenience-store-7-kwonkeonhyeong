package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {

    public String enterOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return readLine().strip();
    }
}
