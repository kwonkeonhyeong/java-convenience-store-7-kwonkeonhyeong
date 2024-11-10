package store.domain.discount;

public class MembershipDiscountPolicy implements DiscountPolicy{

    private static final Long MEMBERSHIP_MAX_AMOUNT = 8000L;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;

    private MembershipDiscountPolicy() {}

    public static MembershipDiscountPolicy newInstance() {
        return new MembershipDiscountPolicy();
    }

    @Override
    public Long getDiscountAmount(Long totalAmount) {
        long discountAmount = (long) ((totalAmount) * (MEMBERSHIP_DISCOUNT_RATE));
        if (discountAmount > MEMBERSHIP_MAX_AMOUNT) {
            discountAmount = MEMBERSHIP_MAX_AMOUNT;
        }
        return discountAmount;
    }
}
