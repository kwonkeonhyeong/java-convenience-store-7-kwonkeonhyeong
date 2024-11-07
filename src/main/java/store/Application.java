package store;

import java.util.List;
import store.repository.StorkRepository;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        StorkRepository storkRepository = new StorkRepository();
        List<Stock> all = storkRepository.findAll();
        System.out.println(all);

        List<Stock> normalStock = storkRepository.findByPromotionIsNotNull();
        System.out.println(normalStock);

        List<Stock> promotionStock = storkRepository.findByPromotionIsNull();
        System.out.println(promotionStock);

        System.out.println(StorkRepository.store);
    }
}
