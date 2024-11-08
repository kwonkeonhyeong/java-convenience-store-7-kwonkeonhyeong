package store.domain.promotion;

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
