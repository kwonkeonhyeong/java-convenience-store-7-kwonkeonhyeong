package store.domain.stock;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StockTest {
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

}