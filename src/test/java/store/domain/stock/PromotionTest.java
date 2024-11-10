package store.domain.stock;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PromotionTest {
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