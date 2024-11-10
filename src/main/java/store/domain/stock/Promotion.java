package store.domain.stock;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.domain.constant.PromotionInputIndex.BUY_INDEX;
import static store.domain.constant.PromotionInputIndex.END_DATE_INDEX;
import static store.domain.constant.PromotionInputIndex.GET_INDEX;
import static store.domain.constant.PromotionInputIndex.NAME_INDEX;
import static store.domain.constant.PromotionInputIndex.START_DATE_INDEX;
import static store.util.constant.Delimiter.COMMA;

import java.time.LocalDate;
import java.time.LocalDateTime;
import store.domain.order.Order;
import store.domain.vo.Buy;
import store.domain.vo.Get;
import store.domain.vo.ProductName;

public class Promotion {

    private final long NON_PROMOTION_QUANTITY = 0L;

    private final ProductName name;
    private final Buy buy;
    private final Get get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(ProductName name, Buy buy, Get get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String input) {
        String[] split = input.split(COMMA.getDelimiter(), -1);
        String name = split[NAME_INDEX.getIndex()];
        String buy = split[BUY_INDEX.getIndex()];
        String get = split[GET_INDEX.getIndex()];
        LocalDate startDate = LocalDate.parse(split[START_DATE_INDEX.getIndex()]);
        LocalDate endDate = LocalDate.parse(split[END_DATE_INDEX.getIndex()]);
        return createPromotion(name, buy, get, startDate, endDate);
    }

    private static Promotion createPromotion(String name, String buy, String get, LocalDate startDate,
                                             LocalDate endDate) {
        return new Promotion(
                ProductName.valueOf(name),
                Buy.valueOf(buy),
                Get.valueOf(get),
                startDate,
                endDate
        );
    }

    public boolean matchesName(String name) {
        return this.name.matchesName(name);
    }

    public boolean isNonPromotionDate() {
        LocalDateTime now = now();
        return now.isBefore(startDate.atStartOfDay()) || now.isAfter(endDate.atStartOfDay());
    }

    public boolean isAdditionalOrder(Order order, Stock stock) {
        Long orderQuantity;
        if ((orderQuantity = order.getQuantity()) < stock.getQuantity()) {
            return orderQuantity % (buy.getBuy() + get.getGet()) == buy.getBuy();
        }
        return false;
    }

    public Long calculateNonPromotionalQuantity(Order order, Stock stock) {
        long promotionQuantity = buy.getBuy() + get.getGet();
        Long orderQuantity, stockQuantity;
        if ((orderQuantity = order.getQuantity()) >= (stockQuantity = stock.getQuantity())) {
            return orderQuantity - ((stockQuantity / (promotionQuantity)) * (promotionQuantity));
        }
        return NON_PROMOTION_QUANTITY;
    }

    public Long calculatePromotionGiftQuantity(Order order, Stock stock) {
        if (isNonPromotionDate()) {
            return NON_PROMOTION_QUANTITY;
        }
        Long orderQuantity, stockQuantity;
        if ((orderQuantity = order.getQuantity()) >= (stockQuantity = stock.getQuantity())) {
            return stockQuantity / (buy.getBuy() + get.getGet());
        }
        return orderQuantity / (buy.getBuy() + get.getGet());
    }

    public Long calculatePromotionQuantity(Long giftQuantity) {
        return giftQuantity * (buy.getBuy() + get.getGet());
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", get=" + get +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
