package store.repository;

import static store.util.constant.FilePath.PROMOTIONS_FILE_PATH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.util.FileHandler;
import store.domain.stock.Promotion;
/*
* 프로모션 데이터를 관리하고 필요한 객체에게 데이터를 제공하는 책임을 부여
* */
public class PromotionRepository {

    private final List<Promotion> store = new ArrayList<>();

    public PromotionRepository() {
        loadData();
    }

    private void loadData() {
        try {
            List<String> inputs = FileHandler.readFromFile(PROMOTIONS_FILE_PATH.getPath());
            registerData(inputs);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Promotion findByPromotionName(String name) {
        return store.stream()
                .filter(promotion -> promotion.matchesName(name))
                .findFirst()
                .orElse(null);
    }

    private void registerData(List<String> inputs) {
        inputs.removeFirst();
        for (String input : inputs) {
            Promotion promotion = Promotion.from(input);
            store.add(promotion);
        }
    }

}
