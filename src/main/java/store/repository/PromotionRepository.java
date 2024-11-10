package store.repository;

import static store.util.constants.FilePath.PROMOTIONS_FILE_PATH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.util.FileHandler;
import store.domain.Promotion;

public class PromotionRepository {

    private final List<Promotion> store = new ArrayList<>();

    public PromotionRepository() {
        loadData();
    }

    private void loadData() {
        try {
            List<String> inputs = FileHandler.readFromFile(PROMOTIONS_FILE_PATH);
            registerData(inputs);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerData(List<String> inputs) {
        inputs.removeFirst();
        for (String input : inputs) {
            Promotion promotion = Promotion.from(input);
            store.add(promotion);
        }
    }

    public Promotion findByPromotionName(String name) {
        return store.stream()
                .filter(promotion -> promotion.matchesName(name))
                .findFirst()
                .orElse(null);
    };
}
