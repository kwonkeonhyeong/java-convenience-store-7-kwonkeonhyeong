package store.domain.order;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {
    @Nested
    @DisplayName("주문_검증_테스트")
    class ValidTest {

        @ParameterizedTest
        @ValueSource(strings = {"(콜라-2)","콜라-2","콜라~2","[콜라,2]"})
        void 주문_형식에_맞지_않는_경우_예외발생(String input) {
            assertThatThrownBy(() -> Order.from(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 주문_상품명_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Order.from("[-2]"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 주문_상품_수량이_0_이하인_경우_예외발생() {
            assertThatThrownBy(() -> Order.from("[콜라--1]"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 주문_상품_수량이_빈_값인_경우_예외발생() {
            assertThatThrownBy(() -> Order.from("[콜라-]"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        @Test
        void 주문_상품_수량이_숫자가_아닌_경우_예외발생() {
            assertThatThrownBy(() -> Order.from("[콜라-하나]"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

    }

    @Nested
    @DisplayName("주문_기능_테스트")
    class FunctionTest {

        private Order order;

        @BeforeEach
        void beforeEach() {
            order = Order.from("[콜라-10]");
        }

        @Test
        void 주문_수량_증가_확인() {
            order.addQuantity();
            assertThat(order.getQuantity()).isEqualTo(11);
        }

        @Test
        void 주문_수량_감소_확인() {
            order.decreaseQuantity(3L);
            assertThat(order.getQuantity()).isEqualTo(7);
        }

    }
}