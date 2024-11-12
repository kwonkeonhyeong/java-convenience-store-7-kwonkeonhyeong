package store;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

public class CustomApplicationTest extends NsTest {

    @Test
    void 프로모션_적용_가능_상품_수량_적게_구매할때_무료증정수량_추가_안내_확인() {
        assertSimpleTest(() -> {
            run("[콜라-2]", "Y", "N", "N");
            assertThat(output()).contains(
                    "현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
            );
        });
    }

    @Test
    void 프로모션_적용_가능_상품_수량_적게_구매_시_무료증정수량_추가하면_수량_증가_확인() {
        assertSimpleTest(() -> {
            run("[콜라-2]", "Y", "N", "N");
            assertThat(output()).contains(
                    "콜라         3     3,000"
            );
        });
    }

    @Test
    void 프로모션_적용_가능_상품_수량_적게_구매_시_무료증정수량_추가_안하면_수량_유지_확인() {
        assertSimpleTest(() -> {
            run("[콜라-2]", "N", "N", "N");
            assertThat(output()).contains(
                    "콜라         2     2,000"
            );
        });
    }

    @Test
    void 프로모션_재고_부족_시_일반_상품으로_구매함_확인() {
        assertSimpleTest(() -> {
            run("[콜라-15]", "Y", "N", "Y","[물-5]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "현재 콜라 6개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",
                    "콜라         15    15,000",
                    "콜라         3",
                    "- 콜라 1,000원 재고 없음 탄산2+1",
                    "- 콜라 1,000원 5개"
            );
        });
    }

    @Test
    void 프로모션_재고_부족_시_일반_상품으로_구매_안함_확인() {
        assertSimpleTest(() -> {
            run("[콜라-15]", "N", "N", "Y","[물-5]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "현재 콜라 6개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",
                    "콜라         9     9,000",
                    "콜라         3",
                    "- 콜라 1,000원 1개 탄산2+1",
                    "- 콜라 1,000원 10개"
            );
        });
    }

    @Test
    void 여러_개의_상품_구매_시_재고_최신화_확인() {
        assertSimpleTest(() -> {
            run("[콜라-3],[에너지바-5]", "N", "Y", "[물-5]", "N", "N");
            assertThat(output())
                    .contains(
                            "- 콜라 1,000원 10개 탄산2+1",
                            "- 콜라 1,000원 7개 탄산2+1",
                            "- 에너지바 2,000원 5개",
                            "- 에너지바 2,000원 재고 없음"
                    );
        });
    }

    @Test
    void 멤버쉽_할인_금액_적용_확인() {
        assertSimpleTest(() -> {
            run("[물-10],[에너지바-5]", "Y", "N");
            assertThat(output())
                    .contains(
                            "멤버십할인            -4,500"
                    );
        });
    }

    @Test
    void 멤버쉽_적용_안함_확인() {
        assertSimpleTest(() -> {
            run("[물-10],[에너지바-5]", "N", "N");
            assertThat(output())
                    .contains(
                            "멤버십할인            -0"
                    );
        });
    }

    @Test
    void 멤버쉽_할인_최대_금액_8000원_확인() {
        assertSimpleTest(() -> {
            run("[정식도시락-6]", "Y", "N");
            assertThat(output())
                    .contains(
                            "멤버십할인            -8,000"
                    );
        });
    }

    @Test
    void 프로모션_할인_금액_확인() {
        assertSimpleTest(() -> {
            run("[콜라-9]", "N", "N");
            assertThat(output())
                    .contains(
                            "행사할인             -3,000"
                    );
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }

}
