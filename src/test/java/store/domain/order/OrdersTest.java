package store.domain.order;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrdersTest {

    @Test
    void 전체_주문을_구분자_콤마로_분리_후_개별_주문_생성() {
        Orders orders = Orders.from("[콜라-3],[에너지바-5]");
        List<Order> getOrders = orders.getOrders();
        assertThat(getOrders.size()).isEqualTo(2);
    }

    @Test
    void 개별_주문_구분자가_콤마가_아닌경우_예외발생() {
        assertThatThrownBy(() -> Orders.from("[콜라-3]|[에너지바-5]"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"","[콜라-3],,[에너지바-5]"})
    void 주문이_빈_값인_경우_예외발생() {
        assertThatThrownBy(() -> Orders.from(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @Test
    void 중복된_주문이_존재하는_경우_예외발생() {
        assertThatThrownBy(() -> Orders.from("[콜라-3],[콜라-5],[에너지바-5]"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }

}