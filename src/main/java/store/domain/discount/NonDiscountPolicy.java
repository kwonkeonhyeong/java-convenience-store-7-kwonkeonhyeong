package store.domain.discount;

import static store.domain.constant.Discount.NON_DISCOUNT;

public class NonDiscountPolicy implements DiscountPolicy {

    private NonDiscountPolicy() {}

    public static NonDiscountPolicy newInstance() {
        return new NonDiscountPolicy();
    }

    @Override
    public Long getDiscountAmount(Long totalAmount) {
        return NON_DISCOUNT.getDiscountAmount(totalAmount);
    }
}
