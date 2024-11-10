package store.domain.constant;

import static store.domain.constant.AmountPattern.AMOUNT_UNIT;
import static store.domain.constant.AmountPattern.DISCOUNT_AMOUNT_UNIT;
import static store.domain.constant.BillComponent.BLANK;
/*
* 영수증 아이템 출력 패턴을 관리
* 영수증 출력 패턴에 맞게 영수증 항목을 만들어 주는 책임 부여
* */
public enum BillPattern {

    ITEM_FORMAT("%-10s %-5s %-10s\n");

    private final String item;

    BillPattern(String item) {
        this.item = item;
    }

    public String formatPaymentSummaryItem(BillComponent component, Long quantity, Long amount) {
        if (quantity == null) {
            String formattedPrice = String.format(DISCOUNT_AMOUNT_UNIT.getPattern(), amount);
            return String.format(item, component.getComponent(), BLANK.getComponent(), formattedPrice);
        }
        String formattedPrice = String.format(AMOUNT_UNIT.getPattern(), amount);
        return String.format(item, component.getComponent(), quantity, formattedPrice);
    }

    public String formatPaymentAmountItem(BillComponent component, Long amount) {
        String formattedPrice = String.format(AMOUNT_UNIT.getPattern(), amount);
        return String.format(item, component.getComponent(), BLANK.getComponent(), formattedPrice);
    }

    public String formatOrderItem(String productName, Long quantity, Long amount) {
        if (amount == null) {
            return String.format(item, productName, quantity, BLANK.getComponent());
        }
        String formattedPrice = String.format(AMOUNT_UNIT.getPattern(), amount);
        return String.format(item, productName, quantity, formattedPrice);
    }

    public String formatHeader(String name, String quantity, String amount) {
        return String.format(item, name, quantity, amount);
    }

}
