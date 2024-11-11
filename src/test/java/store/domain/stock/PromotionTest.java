package store.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.order.Order;

class PromotionTest {

    @Nested
    @DisplayName("프로모션_검증_테스트")
    class ValidTest {

        @Test
        void 프로모션_이름이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from(",2,1,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 프로모션_Buy가_0_이하인_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from("탄산2+1,-1,1,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] Buy은 0 이상이어야 합니다.");
        }
        @Test
        void 프로모션_Buy가_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from("탄산2+1,,1,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 프로모션_Buy가_숫자가_아닌_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from("탄산2+1,둘,1,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 프로모션_Get이_0_이하인_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from("탄산2+1,2,-1,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] Get은 0 이상이어야 합니다.");
        }

        @Test
        void 프로모션_Get이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from("탄산2+1,2,,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 프로모션_Get이_숫자가_아닌_경우_예외발생() {
            assertThatThrownBy(() -> Promotion.from("탄산2+1,2,하나,2024-01-01,2024-12-31"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

    }

    @Nested
    @DisplayName("프로모션_기능_테스트")
    class FunctionTest {

        private Stock stock;
        private Promotion promotion;
        private Order order;

        @BeforeEach
        void beforeEach() {
            stock = Stock.from("콜라,1000,10,탄산2+1");
            promotion = Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31");
            order = Order.from("[콜라-10]");
        }

        @Test
        void 프로모션_이름_일치_여부_확인 () {
            assertThat(promotion.matchesName("탄산2+1")).isTrue();
        }

        @Test
        void 프로모션_적용_가능한_날_확인 () {
            assertThat(promotion.isNonPromotionDate()).isFalse();
        }

        @Test
        void 프로모션_적용_불가능한_날인지_확인 () {
            Promotion promotion = Promotion.from("탄산2+1,2,1,2023-01-01,2023-12-31");
            assertThat(promotion.isNonPromotionDate()).isTrue();
        }

        @Test
        void 프로모션_적용_상품을_적게_가져온_경우_확인() {
            Order anotherOrder = Order.from("[콜라-2]");
            assertThat(promotion.isAdditionalOrder(anotherOrder,stock)).isTrue();
        }

        @Test
        void 구입_수량이_프로모션_재고_초과_시_프로모션_적용하지_않는_수량_계산() {
            Order anotherOrder = Order.from("[콜라-15]");
            Long quantity = promotion.calculateNonPromotionalQuantity(anotherOrder, stock);
            assertThat(quantity).isEqualTo(6);
        }

        @Test
        void 프로모션_증정_수량_계산() {
            Long giftQuantity = promotion.calculatePromotionGiftQuantity(order, stock);
            assertThat(giftQuantity).isEqualTo(3);
        }

        @Test
        void 프로모션_적용_수량_계산() {
            Long giftQuantity = promotion.calculatePromotionGiftQuantity(order, stock);
            assertThat(promotion.calculatePromotionQuantity(giftQuantity)).isEqualTo(9);
        }
    }

}