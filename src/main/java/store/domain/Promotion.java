package store.domain;

import java.time.LocalDate;

public class Promotion {

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
        String[] split = input.split(",");
        String name = split[0];
        Long buy = Long.parseLong(split[1]);
        Long get = Long.parseLong(split[2]);
        LocalDate startDate = LocalDate.parse(split[3]);
        LocalDate endDate = LocalDate.parse(split[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public boolean matchesName(String name) {
        return this.name.equals(name);
    }

    public boolean isNonPromotionDate() {
        LocalDate nowDate = LocalDate.now();
        return nowDate.isBefore(startDate) && nowDate.isAfter(endDate);
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
        return 0L;
    }

    public Long calculatePromotionGiftQuantity(Order order, Stock stock) {
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
