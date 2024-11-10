package store.domain.constant;
/*
* 금액과 관련된 포맷 패턴을 관리
* */
public enum AmountPattern {

    AMOUNT_UNIT("%,d"),
    DISCOUNT_AMOUNT_UNIT("-%,d");

    private final String pattern;

    AmountPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

}
