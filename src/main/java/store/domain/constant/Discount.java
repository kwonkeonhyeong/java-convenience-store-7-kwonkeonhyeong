package store.domain.constant;

public enum Discount {

    MEMBERSHIP_DISCOUNT(8000L, 0.3),
    NON_DISCOUNT(0L, 0);

    private final long maxAmount;
    private final double rate;

    Discount(Long maxAmount, double rate) {
        this.maxAmount = maxAmount;
        this.rate = rate;
    }

    public Long getDiscountAmount(Long totalAmount) {
        long discountAmount = (long) ((totalAmount) * (rate));
        if (discountAmount > maxAmount) {
            discountAmount = maxAmount;
        }
        return discountAmount;
    }

}
