package store.domain.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountPolicyTest {

    private MembershipDiscountPolicy membershipDiscountPolicy;
    private NonDiscountPolicy nonDiscountPolicy;

    @BeforeEach
    void beforeEach() {
        membershipDiscountPolicy = MembershipDiscountPolicy.newInstance();
        nonDiscountPolicy = NonDiscountPolicy.newInstance();
    }

    @Test
    void 멤버쉽_할인_정책_할인_금액_확인() {
        Assertions.assertThat(membershipDiscountPolicy.getDiscountAmount(10000L)).isEqualTo(3000);
    }

    @Test
    void 멤버쉽_할인_정책_최대_할인_금액_8000원_확인() {
        Assertions.assertThat(membershipDiscountPolicy.getDiscountAmount(50000L)).isEqualTo(8000);
    }

    @ParameterizedTest
    @ValueSource(longs = {10000L,20000L,30000L})
    void 할인_정책_적용_안함_금액_확인(long amount) {
        Assertions.assertThat(nonDiscountPolicy.getDiscountAmount(amount)).isEqualTo(0);
    }

}