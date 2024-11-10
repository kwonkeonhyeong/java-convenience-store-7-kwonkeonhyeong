package store.domain.constant;
/*
* 영수증 view에 필요한 컴포넌트 관리
* */
public enum BillComponent {

    TOTAL_PROMOTION_DISCOUNT_AMOUNT_LABEL("행사할인"),
    MEMBERSHIP_DISCOUNT_LABEL("멤버십할인"),
    TOTAL_PURCHASE_AMOUNT_LABEL("총구매액"),
    PAYMENT_AMOUNT_LABEL("내실돈"),
    BLANK(""),
    ORDER_HEADER("==============W 편의점================\n"),
    PRODUCT_HEADER("상품명"),
    QUANTITY_HEADER("수량"),
    AMOUNT_HEADER("금액"),
    PROMOTION_HEADER("=============증정================\n"),
    SUMMARY_HEADER("===================================\n");

    private final String component;

    BillComponent(String component) {
        this.component = component;
    }

    public String getComponent() {
        return component;
    }

}
