package store.domain;

import static camp.nextstep.edu.missionutils.DateTimes.now;
import static store.domain.constant.PromotionInputIndex.BUY_INDEX;
import static store.domain.constant.PromotionInputIndex.END_DATE_INDEX;
import static store.domain.constant.PromotionInputIndex.GET_INDEX;
import static store.domain.constant.PromotionInputIndex.NAME_INDEX;
import static store.domain.constant.PromotionInputIndex.START_DATE_INDEX;
import static store.util.constant.Delimiter.COMMA;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {

    private final long NON_PROMOTION_QUANTITY = 0L;

    private final String name;
    private final Long buy;
    private final Long get;
    private final LocalDate startDate;
    private final LocalDate endDate;


    private Promotion(String name, Long buy, Long get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String input) {
        String[] split = input.split(COMMA.getDelimiter());
        String name = split[NAME_INDEX.getIndex()];
        Long buy = Long.parseLong(split[BUY_INDEX.getIndex()]);
        Long get = Long.parseLong(split[GET_INDEX.getIndex()]);
        LocalDate startDate = LocalDate.parse(split[START_DATE_INDEX.getIndex()]);
        LocalDate endDate = LocalDate.parse(split[END_DATE_INDEX.getIndex()]);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public boolean matchesName(String name) {
        return this.name.equals(name);
    }

    public boolean isNonPromotionDate() {
        LocalDateTime now = now();
        return now.isBefore(startDate.atStartOfDay()) || now.isAfter(endDate.atStartOfDay());
    }

    public boolean isAdditionalOrder(Order order, Stock stock) {
        Long orderQuantity;
        if ((orderQuantity = order.getQuantity()) < stock.getQuantity()) {
            return orderQuantity%(buy+get) == buy;
        }
        return false;
    }

    public Long calculateNonPromotionalQuantity(Order order, Stock stock) {
        Long orderQuantity, stockQuantity;
        if ((orderQuantity = order.getQuantity()) >= (stockQuantity = stock.getQuantity())) {
            return orderQuantity - ((stockQuantity/(buy+get)) * (buy+get));
        }
        return NON_PROMOTION_QUANTITY;
    }

    public Long calculatePromotionGiftQuantity(Order order, Stock stock) {
        if(isNonPromotionDate()){
            return NON_PROMOTION_QUANTITY;
        }
        Long orderQuantity, stockQuantity;
        if ((orderQuantity = order.getQuantity()) >= (stockQuantity = stock.getQuantity())) {
            return stockQuantity/(buy+get);
        }
        return orderQuantity/(buy+get);
    }

    public Long calculatePromotionQuantity(Long giftQuantity) {
        return giftQuantity * (buy+get);
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
