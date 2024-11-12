package store.domain.discount;

import static store.domain.constant.Discount.MEMBERSHIP_DISCOUNT;

public class MembershipDiscountPolicy implements DiscountPolicy{

    private MembershipDiscountPolicy() {}

    public static MembershipDiscountPolicy newInstance() {
        return new MembershipDiscountPolicy();
    }

    @Override
    public Long getDiscountAmount(Long totalAmount) {
        return MEMBERSHIP_DISCOUNT.getDiscountAmount(totalAmount);
    }
}
