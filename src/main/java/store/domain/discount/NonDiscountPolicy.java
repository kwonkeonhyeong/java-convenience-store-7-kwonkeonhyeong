package store.domain.discount;

public class NonDiscountPolicy implements DiscountPolicy {

    private static final Long NON_DISCOUNT_AMOUNT = 0L;

    private NonDiscountPolicy() {}

    public static NonDiscountPolicy newInstance() {
        return new NonDiscountPolicy();
    }

    @Override
    public Long getDiscountAmount(Long totalAmount) {
        return NON_DISCOUNT_AMOUNT;
    }
}
