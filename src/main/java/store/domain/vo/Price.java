package store.domain.vo;

import java.util.Objects;
/*
* 가격은 변하지 않는 값이라고 생각하여  Vo 생성
* */
public class Price {

    private final Long price;

    private Price(Long price) {
        this.price = price;
    }

    public static Price valueOf(Long price) {
        return new Price(price);
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price price1)) {
            return false;
        }
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }

    @Override
    public String toString() {
        return String.valueOf(price);
    }
}
