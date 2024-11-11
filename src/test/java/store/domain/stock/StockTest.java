package store.domain.stock;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StockTest {

    @Nested
    @DisplayName("재고_검증_테스트")
    class ValidTest {

        @Test
        void 상품명이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from(",1000,10,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 상품_가격이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,,10,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 상품_가격이_0보다_작은_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,-1000,10,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 가격은 0 이상이어야 합니다.");
        }

        @Test
        void 상품_가격이_숫자가_아닌_경우_작은_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,천원,10,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 상품_수량이_0_이하인_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,1000,-1,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 수량은 0 이상이어야 합니다.");
        }
        @Test
        void 상품_수량이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,1000,,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        @Test
        void 상품_수량이_숫자가_아닌_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,1000,하나,탄산2+1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 프로모션_이름이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Stock.from("콜라,1000,10,"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 프로모션이_null_인_경우_프로모션_적용_불가_확인() {
            Stock stock = Stock.from("콜라,1000,10,null");
            assertThat(stock.isApplyPromotion()).isFalse();
        }
    }

    @Nested
    @DisplayName("재고_기능_테스트")
    class FunctionTest {

        private Stock stock;

        @BeforeEach
        void beforeEach() {
            stock = Stock.from("콜라,1000,10,탄산2+1");
        }

        @Test
        void 프로모션이_존재하는_경우_프로모션_적용_가능_확인() {
            assertThat(stock.isApplyPromotion()).isTrue();
        }

        @Test
        void 재고_감소_확인() {
            stock.decreaseQuantity(3L);
            assertThat(stock.getQuantity()).isEqualTo(7);
        }

        @Test
        void 수량에_따른_총_금액_확인() {
            assertThat(stock.calculateAmount(3L)).isEqualTo(3000);
        }

        @Test
        void 재고와_상품_이름_동일한지_확인() {
            assertThat(stock.matchesProductName("콜라")).isTrue();
        }

    }
}