package store.domain.discount;
/*
* 할인 정책에 다형성을 부여
* 할인 정책을 유동적으로 관리함으로써 맴버십 할인 이외에 추가적인 할인 정책 부여
* */
public interface DiscountPolicy {
    Long getDiscountAmount(Long totalAmount);
}
