package store.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.stock.Stock;
import store.domain.vo.ProductName;

class StockRepositoryTest {

    private static StockRepository stockRepository;

    @BeforeAll
    static void beforeAll() {
        stockRepository = new StockRepository();
    }

    @Test
    void 재고에_등록되어_있는_상품명_전체_확인() {
        List<ProductName> uniqueProductNames = stockRepository.findUniqueProductNames();
        assertThat(uniqueProductNames).containsAll(expectedProductNames());
    }

    @Test
    void 상품_이름으로_프로모션_재고_찾기() {
        Stock stock = stockRepository.findByProductNameAndPromotionIsNotNull("콜라");
        ProductName productName = stock.getProductName();
        assertThat(productName.getName()).isEqualTo("콜라");
    }

    @Test
    void 상품_이름으로_프로모션_재고_찾고_없으면_NULL() {
        Stock stock = stockRepository.findByProductNameAndPromotionIsNotNull("물");
        assertThat(stock).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"콜라","에너지바","물"})
    void 상품_이름으로_일반_재고_찾기(String name) {
        Stock stock = stockRepository.findByProductNameAndPromotionIsNull(name);
        ProductName productName = stock.getProductName();
        assertThat(productName.getName()).isEqualTo(name);
    }

    @Test
    void 상품_이름으로_일반_재고_찾고_없으면_NULL() {
        Stock stock = stockRepository.findByProductNameAndPromotionIsNull("없는상품");
        assertThat(stock).isNull();
    }

    @Test
    void 상품_이름에_해당하는_재고_상품이_존재하면_TRUE() {
        assertThat(stockRepository.hasStockWith("콜라")).isTrue();
    }

    @Test
    void 상품_이름에_해당하는_재고_상품이_존재하지_않으면_FALSE() {
        assertThat(stockRepository.hasStockWith("없는상품")).isFalse();
    }

    @Test
    void 상품_이름에_해당하는_일반재고_프로모션재고_모두_찾기() {
        assertThat(stockRepository.findByProductName("콜라").size()).isEqualTo(2);
    }

    @Test
    void 상품_이름에_해당하는_일반_재고_수량_차감하기() {
        stockRepository.decreaseQuantityWhereNameAndPromotionIsNull("콜라",3L);
        Stock stock = stockRepository.findByProductNameAndPromotionIsNull("콜라");
        assertThat(stock.getQuantity()).isEqualTo(7);
    }

    @Test
    void 상품_이름에_해당하는_프로모션_재고_수량_차감하기() {
        stockRepository.decreaseQuantityWhereNameAndPromotionIsNotNull("콜라",3L);
        Stock stock = stockRepository.findByProductNameAndPromotionIsNotNull("콜라");
        assertThat(stock.getQuantity()).isEqualTo(7);
    }

    private List<ProductName> expectedProductNames() {
        List<String> names = List.of("콜라", "사이다", "오렌지주스", "탄산수", "물", "비타민워터",
                "감자칩", "초코바", "에너지바", "정식도시락", "컵라면");
        return names.stream().map(ProductName::valueOf).toList();
    }

}