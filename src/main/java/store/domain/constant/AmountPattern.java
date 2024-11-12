package store.domain.constant;

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
