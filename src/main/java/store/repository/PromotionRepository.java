package store.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.util.FileHandler;
import store.domain.Promotion;

public class PromotionRepository {

    private static final List<Promotion> store = new ArrayList<>();

    static {
        try {
            List<String> inputs = FileHandler.readFromFile("src/main/resources/promotions.md");
            inputs.removeFirst();
            for (String input : inputs) {
                Promotion promotion = Promotion.from(input);
                store.add(promotion);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Promotion findByPromotionName(String name) {
        return store.stream()
                .filter(promotion -> promotion.matchesName(name))
                .findFirst()
                .orElse(null);
    };
}
