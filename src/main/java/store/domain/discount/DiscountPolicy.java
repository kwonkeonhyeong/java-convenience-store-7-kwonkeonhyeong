package store.domain.discount;

public interface DiscountPolicy {
    Long getDiscountAmount(Long totalAmount);
}
