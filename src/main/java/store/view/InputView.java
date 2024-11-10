package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;
/*
* 사용자에게 값을 받아오고 이를 반환한다.
* 여기서 기본적인 빈값 검증 등 input value에 대한 검증을 진행해야 할지 말지 고민이 많다.
* inputview에서 한번 검증을 진행하고 model에서 한번 더 검증하면 더욱 안전한 프로그램이 될 것이긴 하다. -> 리펙토링 고려...
* */
public class InputView {

    private static final String ORDER_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String ADD_ORDER_FORMAT = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String APPLY_PROMOTION_FORMAT = "현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final String APPLY_MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String ANOTHER_ORDER_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String enterOrder() {
        System.out.println();
        System.out.println(ORDER_MESSAGE);
        return readLine().strip();
    }

    public String enterAdditionalOrder(String productName) {
        System.out.println();
        System.out.printf(ADD_ORDER_FORMAT, productName);
        return readLine().strip();
    }

    public String enterNonPromotionPurchase(String productName, Long quantity) {
        System.out.println();
        System.out.printf(
                APPLY_PROMOTION_FORMAT,
                productName,
                quantity
        );
        return readLine().strip();
    }

    public String enterApplicableMembershipDiscount() {
        System.out.println();
        System.out.println(APPLY_MEMBERSHIP_MESSAGE);
        return readLine().strip();
    }

    public String enterAnotherOrder() {
        System.out.println();
        System.out.println(ANOTHER_ORDER_MESSAGE);
        return readLine().strip();
    }

}
