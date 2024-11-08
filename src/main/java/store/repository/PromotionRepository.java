package store.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.FileHandler;
import store.domain.promotion.Promotion;

public class PromotionRepository {

    public static final List<Promotion> store = new ArrayList<>();

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
}
