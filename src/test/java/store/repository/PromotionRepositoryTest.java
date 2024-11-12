package store.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.domain.stock.Promotion;

class PromotionRepositoryTest {

    private static PromotionRepository promotionRepository;

    @BeforeAll
    static void beforeAll() {
        promotionRepository = new PromotionRepository();
    }

    @Test
    void 프로모션_이름으로_프로모션_찾기() {
        Promotion byPromotionName = promotionRepository.findByPromotionName("탄산2+1");
        assertThat(byPromotionName.matchesName("탄산2+1")).isTrue();
    }

    @Test
    void 프로모션_이름으로_프로모션_찾고_없으면_NULL() {
        Promotion byPromotionName = promotionRepository.findByPromotionName("없는프로모션");
        assertThat(byPromotionName).isNull();
    }

}